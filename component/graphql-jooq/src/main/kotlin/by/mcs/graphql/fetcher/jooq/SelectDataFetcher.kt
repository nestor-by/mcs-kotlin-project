package by.mcs.graphql.fetcher.jooq

import by.mcs.graphql.dsl.jooq.relation.JooqRelationType
import by.mcs.graphql.dsl.jooq.type.JooqFieldType
import by.mcs.graphql.dsl.jooq.type.JooqTableType
import by.mcs.graphql.fetcher.ReactorDataFetcher
import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLModifiedType
import graphql.schema.GraphQLType
import org.jooq.*
import org.jooq.impl.DSL.and
import org.jooq.impl.DSL.field
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

class SelectDataFetcher(private val dslContext: DSLContext) : ReactorDataFetcher<List<Record>> {
  private val logger = LoggerFactory.getLogger(SelectDataFetcher::class.java)

  override fun async(environment: DataFetchingEnvironment): Mono<List<Record>> {
    val from = dslContext // SelectWhereStep
        .select(getProjections(environment))
        .from(getTable(environment.fieldType))
    val query = getRelations(environment)
        .fold(from) { a, b -> a.join(b.getTable()).on(b.getCondition()) }
        .where(getQuery(environment))
    logger.info("Many jooq query:\n{}", query.sql)

    return Mono.fromCompletionStage(query
        .limit(environment.getArgument(LIMIT_ARG) ?: DEFAULT_LIMIT)
        .offset(environment.getArgument(OFFSET_ARG) ?: DEFAULT_OFFSET)
        .fetchAsync())
        .map { it }
  }

  private fun getProjections(environment: DataFetchingEnvironment): List<Field<*>> {
    return environment
        .selectionSet.definitions
        .values
        .filterIsInstance<JooqFieldType<*>>()
        .map { it.field }
  }

  private fun getRelations(environment: DataFetchingEnvironment): List<JooqRelationType<*>> {
    val values = environment
        .selectionSet.definitions
        .values
    return values
        .flatMap { listOf(it.type, it) }
        .filterIsInstance<JooqRelationType<*>>()
  }

  private fun graphQLOutputType(fieldType: GraphQLType): GraphQLType {
    return when (fieldType) {
      is GraphQLModifiedType -> graphQLOutputType(fieldType.wrappedType)
      else -> fieldType
    }
  }

  private fun getTable(fieldType: GraphQLType): Table<*> {
    val value = graphQLOutputType(fieldType)
    return when (value) {
      is JooqTableType<*> -> value.table
      else -> throw IllegalArgumentException("Unsupported type $value")
    }
  }

  private fun getQuery(environment: DataFetchingEnvironment): Condition? {
    val arguments = environment.arguments.filter { (a, _) -> !ARGS.contains(a) }
    val haveArguments = arguments.isNotEmpty()
    return if (haveArguments) {
      environment.arguments
          .map { (k, v) -> field(k).eq(v) }
          .reduce { a, b -> and(a, b) }
    } else {
      null
    }
  }

  companion object {
    private const val LIMIT_ARG: String = "limit"
    private const val OFFSET_ARG: String = "offset"
    private const val DEFAULT_LIMIT: Int = 10
    private const val DEFAULT_OFFSET: Int = 0
    private val ARGS = listOf(LIMIT_ARG, OFFSET_ARG)
  }
}
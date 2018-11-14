package by.mcs.graphql.dsl.jooq

import by.mcs.graphql.dsl.jooq.relation.JooqManyToOneType
import by.mcs.graphql.dsl.jooq.relation.JooqOneToManyType
import graphql.schema.GraphQLFieldDefinition
import org.jooq.Condition
import org.jooq.Record
import org.jooq.TableField

infix fun GraphQLFieldDefinition.Builder.dataFetcher(condition: Condition): GraphQLFieldDefinition.Builder {
  return this
}

infix fun <R : Record, T> GraphQLFieldDefinition.Builder.dataFetcher(field: TableField<R, T>): GraphQLFieldDefinition.Builder {
  return this
}

operator fun String.rangeTo(type_: JooqManyToOneType.Builder<*>) = type_.name(this).build()
operator fun String.rangeTo(type_: JooqOneToManyType.Builder<*>) = type_.name(this).build()

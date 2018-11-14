package by.mcs.config

import by.mcs.graphql.dsl.*
import by.mcs.graphql.dsl.jooq.rangeTo
import by.mcs.graphql.dsl.jooq.relation.JooqManyToOneType.Companion.manyToOne
import by.mcs.graphql.dsl.jooq.relation.JooqOneToManyType.Companion.oneToMany
import by.mcs.graphql.dsl.jooq.type.JooqTableType.Companion.newObject
import by.mcs.graphql.fetcher.jooq.SelectDataFetcher
import by.mcs.graphql.jooq.test.Tables.*
import graphql.GraphQL
import graphql.Scalars.GraphQLInt
import graphql.execution.AsyncExecutionStrategy
import graphql.schema.GraphQLList
import graphql.schema.GraphQLSchema
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val schemaJooqModule = Kodein.Module("schemaJooqModule") {
  bind() from singleton {
    try {
      val authorType = newObject(AUTHOR)
          .build()

      val languageType = newObject(LANGUAGE)
          .build()

      val bookType = newObject(BOOK)
          .field("author"..manyToOne(authorType, BOOK.AUTHOR_ID))
          .field("language"..manyToOne(languageType, BOOK.LANGUAGE_ID))
          .build()

      val storeType = newObject(BOOK_STORE)
          .build()


      val authorWithBooksType = newObject(AUTHOR)
          .name("authorWithBooksType")
          .field("books"..oneToMany(bookType, AUTHOR.ID.eq(BOOK.AUTHOR_ID)))
          .build()

      val bookToBookStoreType = newObject(BOOK_TO_BOOK_STORE)
          .field("book"..manyToOne(bookType, BOOK_TO_BOOK_STORE.BOOK_ID))
          .field("store"..manyToOne(storeType, BOOK_STORE.NAME.eq(BOOK_TO_BOOK_STORE.NAME)))
          .build()

      val queryType = newObject("QueryType")
          .field("bookToBook"..GraphQLList(bookToBookStoreType)
              argument (+"limit"..GraphQLInt description " limit")
              argument (+"offset"..GraphQLInt description " offset")
              dataFetcher SelectDataFetcher(instance()))
          .field("languages"..GraphQLList(languageType)
              argument (+"limit"..GraphQLInt description " limit")
              argument (+"offset"..GraphQLInt description " offset")
              dataFetcher SelectDataFetcher(instance()))
          .field("authors"..GraphQLList(authorWithBooksType)
              argument (+"limit"..GraphQLInt description " limit")
              argument (+"offset"..GraphQLInt description " offset")
              dataFetcher SelectDataFetcher(instance()))
          .field("books"..GraphQLList(bookType)
              argument (+"limit"..GraphQLInt description " limit")
              argument (+"offset"..GraphQLInt description " offset")
              dataFetcher SelectDataFetcher(instance()))


      val starWarsSchema = GraphQLSchema
          .newSchema()
          .query(queryType)
          .build()

      GraphQL.newGraphQL(starWarsSchema)
          .queryExecutionStrategy(AsyncExecutionStrategy())
          .build()
    } catch (e: Exception) {
      e.printStackTrace()
      throw IllegalArgumentException(e)
    }
  }
}
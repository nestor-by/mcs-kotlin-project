package by.mcs.config

import by.mcs.graphql.dsl.*
import by.mcs.graphql.fetcher.mongodb.ManyDataFetcher
import by.mcs.graphql.scalar.GraphQLLocalDate
import by.mcs.graphql.scalar.GraphQLLocalDateTime
import graphql.GraphQL
import graphql.Scalars.*
import graphql.execution.AsyncExecutionStrategy
import graphql.schema.GraphQLList
import graphql.schema.GraphQLSchema
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val schemaMongodbModule = Kodein.Module("schemaMongodbModule") {
  bind() from singleton {

    val sexEnum = newEnum("Sex")
        .description("One of the films in the Star Wars Trilogy")
        .value("MAN")
        .value("WOMAN")
        .build()

    val contactType = newObject("Contact")
        .field("category"..GraphQLString)
        .field("phone"..GraphQLString)
        .field("email"..GraphQLString)
        .build()

    val documentType = newObject("Document")
        .field("category"..GraphQLString)
        .field("number"..GraphQLString)
        .field("country"..GraphQLString)
        .field("authority"..GraphQLString)
        .field("dateOfBegin"..GraphQLFloat)
        .build()

    val place = newObject("Place")
        .field("soato"..GraphQLString)
        .field("country"..GraphQLString)
        .field("district"..GraphQLString)
        .field("region"..GraphQLString)
        .field("category"..GraphQLString)
        .field("value"..GraphQLString)
        .build()

    val street = newObject("Street")
        .field("place"..place)
        .field("category"..GraphQLString)
        .field("value"..GraphQLString)
        .build()

    val address = newObject("Address")
        .field("street"..street)
        .field("building"..GraphQLString)
        .field("house"..GraphQLString)
        .field("housing"..GraphQLString)
        .field("zip"..GraphQLString)
        .field("full"..GraphQLString)
        .field("dateOfBegin"..GraphQLString)
        .field("dateOfEnd"..GraphQLString)
        .build()

    val work = newObject("Work")
        .field("company"..GraphQLString)
        .field("position"..GraphQLString)
        .field("phone"..GraphQLString)
        .field("bookerphone"..GraphQLString)
        .field("address"..address)
        .field("dateOfBegin"..GraphQLInt)
        .field("dateOfEnd"..GraphQLInt)
        .build()

    val visa = newObject("Visa")
        .field("country"..GraphQLString)
        .field("dateOfBegin"..GraphQLInt)
        .field("dateOfEnd"..GraphQLInt)
        .build()

    val name = newObject("Name")
        .field("firstName"..GraphQLString)
        .field("lastName"..GraphQLString)
        .field("middleName"..GraphQLInt)
        .build()


    val subjectType = newObject("Subject")
        .field("id"..GraphQLString)
        .field("dateOfCreated"..GraphQLLocalDateTime)
        .field("firstName"..GraphQLString)
        .field("lastName"..GraphQLString)
        .field("middleName"..GraphQLString)
        .field("maidenName"..GraphQLString)
        .field("sex"..sexEnum)
        .field("personalNumber"..GraphQLString)
        .field("dateOfBirth"..GraphQLLocalDate)
        .field("placeOfBirth"..GraphQLString)
        .field("citizenship"..GraphQLString)
        .field("contacts"..GraphQLList(contactType))
        .field("documents"..GraphQLList(documentType))
        .field("addresses"..GraphQLList(address))
        .field("tags"..GraphQLList(GraphQLString))
        .field("description"..GraphQLString)
        .field("key"..GraphQLInt)
        .field("integrations"..GraphQLList(GraphQLString))
        .field("resident"..GraphQLBoolean)
        .field("leadSource"..GraphQLString)
        .field("unp"..GraphQLString)
        .field("sysinfo"..GraphQLString)
        .field("works"..GraphQLList(work))
        .field("vises"..GraphQLList(visa))
        .field("representatives"..GraphQLList(name))
        .field("names"..GraphQLList(name))
//          .field("risks"..GraphQLString)
        .build()


    val queryType = newObject("QueryType")
        .field("subjects"..GraphQLList(subjectType)
            argument (+"limit"..GraphQLInt description " limit")
            argument (+"offset"..GraphQLInt description " offset")
            dataFetcher ManyDataFetcher(instance(), "subjects"))


    val starWarsSchema = GraphQLSchema
        .newSchema()
        .query(queryType)
        .build()
    GraphQL.newGraphQL(starWarsSchema)
        .queryExecutionStrategy(AsyncExecutionStrategy())
        .build()
  }
}
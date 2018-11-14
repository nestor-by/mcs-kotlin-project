import com.google.common.collect.ImmutableMap
import com.rohanprabhu.gradle.plugins.kdjooq.*
import com.rohanprabhu.gradle.plugins.kdjooq.jooqCodegenConfiguration
import org.gradle.api.internal.file.SourceDirectorySetFactory
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.JavaGenerator
import org.jooq.meta.jaxb.CustomType
import org.jooq.meta.jaxb.Logging

plugins {
  id("com.rohanprabhu.kotlin-dsl-jooq")
}

jooqGenerator {
  jooqVersion = deps.versions.jooq
  configuration("default", sourceSet = sourceSets["main"]) {
    configuration = jooqCodegenConfiguration {
      jdbc = jdbc {
        username = "sa"
        password = ""
        driver = "org.h2.Driver"
        url = "jdbc:h2:mem:test;INIT=runscript from './src/main/resources/database.sql'"
      }

      logging = Logging.ERROR
      generator = generator {
        name = JavaGenerator::class.java.name
        database = database {
          includes = "language|author|book|book_store|book_to_book_store"
//          name = org.jooq.meta.xml.XMLDatabase::class.java.name
//          properties = listOf(
//              dbProperty("xml-file", "src/main/resources/database.xml"),
//              dbProperty("dialect", "H2")
//          )
          forcedTypes = listOf(
              forcedType {
                name = "BOOLEAN"
                expression = ".*AUTHOR\\.DISTINGUISHED"
              }
          )
        }
        generate = generate {
          isPojos = false
          isDaos = false
          isFluentSetters = true
        }
        target = target {
          packageName = "by.mcs.graphql.jooq"
          directory = "${project.buildDir}/generated/jooq/primary"
        }
      }
    }
  }
}

dependencies {
  compile(project(":component:graphql-jooq"))
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  implementation("com.h2database:h2:1.4.197")
  jooqGeneratorRuntime("com.h2database:h2:1.4.197")

  implementation(deps.graphql.core)
  implementation(deps.graphql.extendedScalars)

  implementation(deps.kodein.core)
  implementation(deps.reactor.core)
  implementation(deps.reactor.netty)
  implementation(deps.jooq.core)
}
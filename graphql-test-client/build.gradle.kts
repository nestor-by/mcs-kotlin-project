import com.google.common.collect.ImmutableMap
import com.rohanprabhu.gradle.plugins.kdjooq.*
import com.rohanprabhu.gradle.plugins.kdjooq.jooqCodegenConfiguration
import org.gradle.api.internal.file.SourceDirectorySetFactory
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.JavaGenerator
import org.jooq.meta.jaxb.Logging

plugins {
  id("com.apollographql.android")
}

apollo {
  customTypeMapping.set(mapOf(
      "LocalDate" to "java.time.LocalDate",
      "LocalDateTime" to "java.time.LocalDateTime"
  ))
//  schemaFilePath.set("http://localhost:8080/graphql")
  useSemanticNaming.set(true)
  generateModelBuilder.set(true)
  useJavaBeansSemanticNaming.set(true)
  outputPackageName.set("by.mcs.graphql.api")
}


dependencies {
  compile(project(":component:graphql-apollo"))

  implementation(deps.logger.slf4j)
  implementation(deps.logger.logback.core)
  implementation(deps.logger.logback.classic)
  implementation(deps.kodein.core)
}
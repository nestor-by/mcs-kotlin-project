import com.google.common.collect.ImmutableMap
import com.rohanprabhu.gradle.plugins.kdjooq.*
import com.rohanprabhu.gradle.plugins.kdjooq.jooqCodegenConfiguration
import org.gradle.api.internal.file.SourceDirectorySetFactory
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.JavaGenerator
import org.jooq.meta.jaxb.Logging

dependencies {
  compile(project(":component:graphql-mongodb"))

  implementation(deps.mongodb.async)
  implementation(deps.kodein.core)
  implementation(deps.reactor.core)
}
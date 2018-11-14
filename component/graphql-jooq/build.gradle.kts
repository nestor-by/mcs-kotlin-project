dependencies {
  compile(project(":component:graphql-core"))
  compile(project(":component:graphql-mongodb"))

  implementation(deps.jooq.core)
  implementation(deps.kodein.core)
}
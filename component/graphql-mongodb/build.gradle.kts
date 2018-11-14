dependencies {
  compile(project(":component:graphql-core"))

  implementation(deps.kodein.core)
  implementation(deps.mongodb.async)
}
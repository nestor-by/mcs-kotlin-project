dependencies {
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  implementation(deps.graphql.core)
  implementation(deps.graphql.extendedScalars)
  implementation(deps.reactor.core)
  implementation(deps.jackson.core)
  implementation(deps.jackson.jsr310)
}
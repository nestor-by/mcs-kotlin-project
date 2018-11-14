dependencies {
  compile(project(":component:launcher-netty"))
  compile(project(":graphql-test-mongo"))
  compile(project(":graphql-test-jooq"))

  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  compile("com.zaxxer:HikariCP:3.2.0")

  implementation(deps.graphql.core)
  implementation(deps.graphql.extendedScalars)
  implementation(deps.logger.slf4j)
  implementation(deps.logger.logback.core)
  implementation(deps.logger.logback.classic)
  implementation(deps.kodein.core)
  implementation(deps.reactor.core)
  implementation(deps.jackson.core)
  implementation(deps.jackson.jsr310)
}
dependencies {
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  implementation(deps.reactor.core)
  implementation(deps.reactor.netty)
  implementation(deps.kodein.core)
//  implementation("com.beust:klaxon:3.0.1")
  implementation(deps.jackson.core)
  implementation(deps.jackson.jsr310)
}
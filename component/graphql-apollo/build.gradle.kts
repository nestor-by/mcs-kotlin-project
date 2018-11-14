dependencies {

  implementation(deps.reactor.core)
  implementation(deps.apollo.runtime)

  implementation(deps.okhttp.core)
  implementation(deps.okhttp.webSockets)
  implementation(deps.okhttp.debug.loggingInterceptor)
}
package by.mcs.graphql.fetcher

import graphql.ExceptionWhileDataFetching
import graphql.execution.DataFetcherResult
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

interface ReactorDataFetcher<T> : DataFetcher<CompletableFuture<DataFetcherResult<T>>> {
  override fun get(environment: DataFetchingEnvironment): CompletableFuture<DataFetcherResult<T>> {
    return@get async(environment)
        .map { DataFetcherResult(it, emptyList()) }
        .onErrorResume { exception ->
          val path = environment.executionStepInfo.path
          val sourceLocation = environment.executionStepInfo.field.sourceLocation
          Mono.just(DataFetcherResult<T>(null, listOf(
              ExceptionWhileDataFetching(path, exception, sourceLocation)
          )))
        }
        .toFuture()
  }

  fun async(environment: DataFetchingEnvironment): Mono<T>
}
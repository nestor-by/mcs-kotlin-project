package by.mcs.graphql.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment

class ProvidedDataFetcher<T> : DataFetcher<T> {
  override fun get(env: DataFetchingEnvironment): T? {
    return env.getSource<T>()
  }
}
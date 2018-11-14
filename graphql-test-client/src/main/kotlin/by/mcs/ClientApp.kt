package by.mcs

import by.mcs.config.clientModule
import by.mcs.graphql.api.BookToBookQuery
import by.mcs.graphql.apollo.reactor.ReactorClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

object ClientApp : KodeinAware {
  override val kodein = Kodein {
    import(clientModule)
  }

  private val client: ReactorClient by instance()

  @JvmStatic
  fun main(args: Array<String>) {
    client
        .from(BookToBookQuery())
        .map { it.data() }
        .flatMapIterable { x -> x?.bookToBook }
        .subscribe { println(it.book?.author?.firsT_NAME ?: ""+" "+it.store?.name ?: "") }
  }
}

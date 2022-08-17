package studio.komkat.android_newsapp.data

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import studio.komkat.android_newsapp.RxSchedulerProvider
import studio.komkat.android_newsapp.data.remote.NewsService
import studio.komkat.android_newsapp.domain.model.Article
import studio.komkat.android_newsapp.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
  private val service: NewsService,
  private val rxSchedulerProvider: RxSchedulerProvider,
) : ArticleRepository {
  // Single<T> -> Single<R>: map((T) -> R)
  // Single<T> -> Single<R>: flatMap(f: (T) -> Single<R>)
  override fun getTopHeadlines(country: String): Single<List<Article>> =
    service.getTopHeadlines(country)
      .map { it.articles }
      .doOnSuccess { Log.d(TAG, "getTopHeadlines: $it") }
      .doOnError { Log.d(TAG, "getTopHeadlines: $it") }
      .flatMap { articles ->
        saveLocal(articles)
          .onErrorComplete()
          .andThen(Single.just(articles))
      }
      .subscribeOn(rxSchedulerProvider.io)

  private fun saveLocal(articles: List<Article>): Completable = Completable.fromCallable {
    // save to local db
    Thread.sleep(500)
    Log.d("ArticleRepository", "saveLocal: $articles")
  }.subscribeOn(rxSchedulerProvider.io)

  private companion object {
    private const val TAG = "ArticleRepository"
  }
}
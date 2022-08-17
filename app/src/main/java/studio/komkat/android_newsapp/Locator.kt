package studio.komkat.android_newsapp

import retrofit2.create
import studio.komkat.android_newsapp.data.ArticleRepositoryImpl
import studio.komkat.android_newsapp.data.remote.NewsService
import studio.komkat.android_newsapp.data.remote.RetrofitClientInstance
import studio.komkat.android_newsapp.domain.repository.ArticleRepository

object Locator {

  private val newsService by lazy {
    RetrofitClientInstance.retrofitInstance.create<NewsService>()
  }
  private val rxSchedulerProvider by lazy {
    DefaultRxSchedulerProvider()
  }

  private val articleRepository by lazy {
    ArticleRepositoryImpl(
      service = newsService,
      rxSchedulerProvider = rxSchedulerProvider
    )
  }

  fun provideRxSchedulerProvider(): RxSchedulerProvider = rxSchedulerProvider
  fun provideArticleRepository(): ArticleRepository = articleRepository
}
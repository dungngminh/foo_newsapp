package studio.komkat.android_newsapp.domain.repository

import io.reactivex.rxjava3.core.Single
import studio.komkat.android_newsapp.domain.model.Article

interface ArticleRepository {
  fun getTopHeadlines(country: String = "us"): Single<List<Article>>
}
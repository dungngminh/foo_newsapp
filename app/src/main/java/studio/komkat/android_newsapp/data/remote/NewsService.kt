package studio.komkat.android_newsapp.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import studio.komkat.android_newsapp.domain.model.Article

interface NewsService {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String): Single<ArticlesResponse>

}
package studio.komkat.android_newsapp.data.remote

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import studio.komkat.android_newsapp.model.Article

interface NewsService {

    @GET("/top-headlines?country=us")
    fun getTopHeadlines(@Query("country") country: String = "us"): Observable<List<Article>>

}
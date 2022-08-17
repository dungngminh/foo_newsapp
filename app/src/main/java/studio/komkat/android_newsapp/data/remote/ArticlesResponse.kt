package studio.komkat.android_newsapp.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json
import studio.komkat.android_newsapp.domain.model.Article

@Keep
data class ArticlesResponse(
  @Json(name = "status") val status: String, // ok
  @Json(name = "totalResults") val totalResults: Int, // 37
  @Json(name = "articles") val articles: List<Article>
)
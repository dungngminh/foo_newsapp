package studio.komkat.android_newsapp.domain.model

import com.squareup.moshi.Json
import java.util.*

data class Article(
    @field:Json(name = "source") val source: Source?,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "urlToImage") val urlToImage: String?,
    @field:Json(name = "publishedAt") val publishedAt: String?,
    @field:Json(name = "content") val content: String?,
) {
    data class Source(
        @field:Json(name = "id") val id: String?,
        @field:Json(name = "name") val name: String?,
    )

}


package studio.komkat.android_newsapp.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClientInstance {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private const val API_KEY = "10b5229d437349fab888f7742b23e0c0";

    val retrofitInstance: Retrofit by lazy {
        val headersInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("x-api-key", API_KEY)
            chain.proceed(requestBuilder.build())
        }
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .followRedirects(true)
            .addInterceptor(headersInterceptor)
            .build()

        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
    }

}
package com.gabriel.test.githubuserdata.api

import com.gabriel.test.githubuserdata.api.mapping.SearchUserResults
import com.gabriel.test.githubuserdata.api.mapping.UserInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val baseUrl = "https://api.github.com/"

interface GithubAPI {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{username}")
    fun getUser(@Path("username") userName: String): Call<UserInfo>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/users")
    fun searchUserName(
        @Query("q") partialName: String,
        @Query("sort") sort: String = "repositories",
        @Query("order") order: String = "desc"
    ): Call<SearchUserResults>
}

fun initHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder().build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()
}

fun initGithubAPI(httpClient: OkHttpClient): GithubAPI {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(GithubAPI::class.java)
}
package com.example.codingtestforbt.data.remote

import com.example.codingtestforbt.data.remote.dto.NewsResponse
import com.example.codingtestforbt.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}
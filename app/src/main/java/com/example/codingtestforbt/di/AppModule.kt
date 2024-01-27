package com.example.codingtestforbt.di

import com.example.codingtestforbt.data.local.ArticleRemoteKeysDao
import com.example.codingtestforbt.data.local.NewsDao
import com.example.codingtestforbt.data.local.NewsDatabase
import com.example.codingtestforbt.data.remote.NewsApi
import com.example.codingtestforbt.domain.model.ArticleRemoteKeys
import com.example.codingtestforbt.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }



}
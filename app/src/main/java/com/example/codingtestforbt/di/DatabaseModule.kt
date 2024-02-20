package com.example.codingtestforbt.di

import android.content.Context
import androidx.room.Room
import com.example.codingtestforbt.data.local.ArticleDao
import com.example.codingtestforbt.data.local.ArticleRemoteKeysDao
import com.example.codingtestforbt.data.local.NewsDatabase
import com.example.codingtestforbt.util.Constants.ARTICLE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            ARTICLE_DATABASE
        ).fallbackToDestructiveMigration().build()
    }

}
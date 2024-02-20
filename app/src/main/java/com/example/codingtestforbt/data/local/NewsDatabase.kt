package com.example.codingtestforbt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.domain.model.ArticleRemoteKeys

@Database(entities = [Article::class, ArticleRemoteKeys::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeysDao(): ArticleRemoteKeysDao
}
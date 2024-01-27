package com.example.codingtestforbt.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingtestforbt.domain.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM Article")
    fun getAllArticles(): PagingSource<Int, Article>

    @Query("DELETE FROM Article")
    suspend fun deleteAllArticles()
}
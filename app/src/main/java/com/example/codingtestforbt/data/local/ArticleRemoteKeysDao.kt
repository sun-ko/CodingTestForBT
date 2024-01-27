package com.example.codingtestforbt.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingtestforbt.domain.model.ArticleRemoteKeys

@Dao
interface ArticleRemoteKeysDao {

    @Query("SELECT * FROM article_remote_keys_table WHERE url =:id")
    suspend fun getRemoteKeys(id: String): ArticleRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<ArticleRemoteKeys>)

    @Query("DELETE FROM article_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}
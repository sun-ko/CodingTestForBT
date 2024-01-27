package com.example.codingtestforbt.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article_remote_keys_table")
data class ArticleRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val prevPage: Int?,
    val nextPage: Int?,
): Parcelable
package com.example.codingtestforbt.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.codingtestforbt.util.Constants.ARTICLE_REMOTE_KEYS_TABLE
import kotlinx.parcelize.Parcelize


@Entity(tableName = ARTICLE_REMOTE_KEYS_TABLE)
data class ArticleRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val prevPage: Int?,
    val nextPage: Int?
)
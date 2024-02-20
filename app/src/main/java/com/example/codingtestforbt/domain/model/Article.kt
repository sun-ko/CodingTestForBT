package com.example.codingtestforbt.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.codingtestforbt.util.Constants.ARTICLE_TABLE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Entity(tableName = ARTICLE_TABLE)
data class Article(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    @Embedded
    val source: Source,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
): Parcelable
package com.example.codingtestforbt.data.remote.dto

import com.example.codingtestforbt.domain.model.Article


data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
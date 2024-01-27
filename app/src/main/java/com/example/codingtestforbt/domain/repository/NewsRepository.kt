package com.example.codingtestforbt.domain.repository

import androidx.paging.PagingData
import com.example.codingtestforbt.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getDefaultNews(query: String): Flow<PagingData<Article>>
    fun getSearchNews(query: String): Flow<PagingData<Article>>
}
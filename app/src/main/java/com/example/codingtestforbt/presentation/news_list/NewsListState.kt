package com.example.codingtestforbt.presentation.news_list

import androidx.paging.PagingData
import com.example.codingtestforbt.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class NewsListState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0,
    val articles: Flow<PagingData<Article>>? = null
)
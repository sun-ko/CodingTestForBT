package com.example.codingtestforbt.presentation.news_list

data class NewsListState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val scrollValue: Int = 0,
    val maxScrollingValue: Int = 0,
)
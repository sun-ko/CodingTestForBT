package com.example.codingtestforbt.presentation.news_list

sealed class NewsListEvent {
    data class UpdateScrollValue(val newValue: Int): NewsListEvent()
    data class UpdateMaxScrollingValue(val newValue: Int): NewsListEvent()
    data class UpdateSearchQuery(val searchQuery: String): NewsListEvent()

    object RefreshDefaultNews: NewsListEvent()
    object GetNews: NewsListEvent()

}
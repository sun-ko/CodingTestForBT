package com.example.codingtestforbt.presentation.news_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.domain.usecase.GetNews
import com.example.codingtestforbt.domain.usecase.SearchNews
import com.example.codingtestforbt.repository.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getDefaultNews: GetNews,
    private val getSearchNews: SearchNews,
) : ViewModel() {
    var state = mutableStateOf(NewsListState())
        private set

    private val newsFlow = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val newsState: StateFlow<PagingData<Article>> = newsFlow

    init {
        getDefaultNews()
    }

    fun onEvent(event: NewsListEvent) {
        when (event) {
            is NewsListEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is NewsListEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
            is NewsListEvent.RefreshDefaultNews -> onRefresh()
            is NewsListEvent.UpdateSearchQuery -> {
                state.value = state.value.copy(searchQuery = event.searchQuery)
            }
            is NewsListEvent.GetNews -> getSearchNews()
        }
    }

    private fun updateScrollValue(newValue: Int) {
        state.value = state.value.copy(scrollValue = newValue)
    }

    private fun updateMaxScrollingValue(newValue: Int) {
        state.value = state.value.copy(maxScrollingValue = newValue)
    }

    private fun getDefaultNews() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val articles = getDefaultNews("bitcoin").cachedIn(viewModelScope)
            newsFlow.value = articles.first()
            state.value = state.value.copy(isLoading = false)
        }
    }

    private fun getSearchNews() {
        viewModelScope.launch {
            val articles = getSearchNews(state.value.searchQuery).cachedIn(viewModelScope)
            newsFlow.value = articles.first()
        }
    }

    private fun onRefresh() {
        getDefaultNews()
    }
}
package com.example.codingtestforbt.presentation.news_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.codingtestforbt.data.local.NewsDatabase
import com.example.codingtestforbt.domain.usecase.GetNews
import com.example.codingtestforbt.domain.usecase.SearchNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getDefaultNews: GetNews,
    private val getSearchNews: SearchNews,
    private val newsDatabase: NewsDatabase
) : ViewModel() {

    private val _state = mutableStateOf(NewsListState())
    val newsState: State<NewsListState> = _state

    init {
        getDefaultNews()
    }

    fun onEvent(event: NewsListEvent) {
        when (event) {
            is NewsListEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is NewsListEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
            is NewsListEvent.RefreshDefaultNews -> onRefresh()
            is NewsListEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
            }

            is NewsListEvent.GetNews -> getSearchNews()
        }
    }

    private fun updateScrollValue(newValue: Int) {
        _state.value = _state.value.copy(scrollValue = newValue)
    }

    private fun updateMaxScrollingValue(newValue: Int) {
        _state.value = _state.value.copy(maxScrollingValue = newValue)
    }

    private fun getDefaultNews() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val articles = getDefaultNews("bitcoin").cachedIn(viewModelScope)
            _state.value = _state.value.copy(articles = articles)
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun getSearchNews() {
        viewModelScope.launch {
            val articles = getSearchNews(_state.value.searchQuery).cachedIn(viewModelScope)
            _state.value = _state.value.copy(articles = articles)
        }
    }

    private fun onRefresh() {
        getDefaultNews()
    }
}

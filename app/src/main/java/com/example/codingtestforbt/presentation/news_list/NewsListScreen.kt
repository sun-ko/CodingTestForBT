package com.example.codingtestforbt.presentation.news_list

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.presentation.Dimens.MediumPadding1
import com.example.codingtestforbt.presentation.common.ArticlesList
import com.example.codingtestforbt.presentation.common.SearchBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun NewsListScreen(
    state: NewsListState,
    event: (NewsListEvent) -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {

        SearchBar(
            modifier = Modifier
                .padding(horizontal = MediumPadding1)
                .fillMaxWidth(),
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(NewsListEvent.UpdateSearchQuery(it)) },
            onSearch = { event(NewsListEvent.GetNews) }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        val scrollState = rememberScrollState(initial = state.scrollValue)

        // Update the maxScrollingValue
        LaunchedEffect(key1 = scrollState.maxValue) {
            event(NewsListEvent.UpdateMaxScrollingValue(scrollState.maxValue))
        }
        // Save the state of the scrolling position
        LaunchedEffect(key1 = scrollState.value) {
            event(NewsListEvent.UpdateScrollValue(scrollState.value))
        }

        // Animate the scrolling
        LaunchedEffect(key1 = state.maxScrollingValue) {
            delay(500)
            if (state.maxScrollingValue > 0) {
                scrollState.animateScrollTo(
                    value = state.maxScrollingValue,
                    animationSpec = infiniteRepeatable(
                        tween(
                            durationMillis = (state.maxScrollingValue - state.scrollValue) * 50_000 / state.maxScrollingValue,
                            easing = LinearEasing,
                            delayMillis = 1000
                        )
                    )
                )
            }
        }

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                event(NewsListEvent.UpdateSearchQuery(""))
                event(NewsListEvent.RefreshDefaultNews)
            }
        ) {
            state.articles?.let {
                val articles = it.collectAsLazyPagingItems()
                ArticlesList(
                    modifier = Modifier.padding(horizontal = MediumPadding1),
                    articles = articles,
                    onClick = navigateToDetails
                )
            }
        }
    }
}
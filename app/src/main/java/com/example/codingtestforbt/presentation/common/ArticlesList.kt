package com.example.codingtestforbt.presentation.common

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LOGGER
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.presentation.Dimens.ExtraSmallPadding2
import com.example.codingtestforbt.presentation.Dimens.MediumPadding1
import com.example.codingtestforbt.presentation.news_list.components.ArticleCard

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
    if (articles.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(
            count = articles.size,
        ) {
            articles[it].let { article ->
                ArticleCard(article = article, onClick = { onClick(article) })
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    Log.d("news articles from db", articles.itemCount.toString())
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            val pagingResult = handlePagingResult(articles)

            if (pagingResult.isLoading) {
                ShimmerEffect()
            }else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1)
                ) {
                    items(articles.itemCount) { index ->
                        val article = articles[index]
                        if (article != null){
                            ArticleCard(article = article, onClick = { onClick(article) })
                        }else{
                            EmptyScreen()
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun handlePagingResult(articles: LazyPagingItems<Article>): PagingResult {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> PagingResult(isLoading = true)
        error != null -> PagingResult(hasError = true, error = error)
        loadState.append.endOfPaginationReached -> PagingResult(isPageEnd = true)
        else -> PagingResult(isLoading = false)
    }
}

data class PagingResult(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val error: LoadState.Error? = null,
    val isPageEnd: Boolean = false
)

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}
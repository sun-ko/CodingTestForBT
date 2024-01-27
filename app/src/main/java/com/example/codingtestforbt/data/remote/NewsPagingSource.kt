package com.example.codingtestforbt.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.codingtestforbt.domain.model.Article

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, Article>() {


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getNews(searchQuery = query, page = page, pageSize= 50)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title } //Remove duplicates

            val nextPage = if (totalNewsCount >= newsResponse.totalResults) null else page + 1
            LoadResult.Page(
                data = articles,
                nextKey = nextPage,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}


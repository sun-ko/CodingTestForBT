package com.example.codingtestforbt.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.codingtestforbt.data.local.NewsDao
import com.example.codingtestforbt.data.remote.NewsApi
import com.example.codingtestforbt.data.remote.NewsPagingSource
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun getDefaultNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                NewsPagingSource(newsApi = newsApi, query = query)
            }
        ).flow
    }

    override fun getSearchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                NewsPagingSource(newsApi = newsApi, query = query)
            }
        ).flow
    }
}
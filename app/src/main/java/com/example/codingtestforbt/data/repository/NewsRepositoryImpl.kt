package com.example.codingtestforbt.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.codingtestforbt.data.local.NewsDatabase
import com.example.codingtestforbt.data.remote.NewsApi
import com.example.codingtestforbt.data.remote.NewsRemoteMediator
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
) : NewsRepository {
    override fun getDefaultNews(query: String): Flow<PagingData<Article>> {
        val pagingSourceFactory = { newsDatabase.articleDao().getAllArticles() }
        return Pager(
            config = PagingConfig(pageSize = 50),
            remoteMediator = NewsRemoteMediator(
                newsApi = newsApi,
                newsDatabase = newsDatabase,
                query
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getSearchNews(query: String): Flow<PagingData<Article>> {
        val pagingSourceFactory = { newsDatabase.articleDao().getAllArticles() }
        return Pager(
            config = PagingConfig(pageSize = 50),
            remoteMediator = NewsRemoteMediator(
                newsApi = newsApi,
                newsDatabase = newsDatabase,
                query
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
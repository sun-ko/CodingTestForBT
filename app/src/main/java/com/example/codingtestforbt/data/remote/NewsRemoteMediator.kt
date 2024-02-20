package com.example.codingtestforbt.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.codingtestforbt.data.local.NewsDatabase
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.domain.model.ArticleRemoteKeys

@ExperimentalPagingApi
class NewsRemoteMediator(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase,
    private val query: String
) : RemoteMediator<Int, Article>() {

    private val newsDao = newsDatabase.articleDao()
    private val articleRemoteKeysDao = newsDatabase.articleRemoteKeysDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val newsResponse =
                newsApi.getNews(searchQuery = query, page = currentPage, pageSize = 50)
            val articlesFromRemote =
                newsResponse.articles.distinctBy { it.title } //Remove duplicates
            val endOfPaginationReached = newsResponse.articles.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDao.deleteAllArticles()
                    articleRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = articlesFromRemote.map { article ->
                    ArticleRemoteKeys(
                        url = article.url,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                articleRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                newsDao.insertArticles(articles = articlesFromRemote)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { url ->
                articleRemoteKeysDao.getRemoteKeys(url = url)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                articleRemoteKeysDao.getRemoteKeys(url = it.url)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                articleRemoteKeysDao.getRemoteKeys(url = it.url)
            }
    }

}
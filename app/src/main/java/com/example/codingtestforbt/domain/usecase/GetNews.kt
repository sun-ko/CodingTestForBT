package com.example.codingtestforbt.domain.usecase

import androidx.paging.PagingData
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Article>> {
        return newsRepository.getDefaultNews(query)
    }
}
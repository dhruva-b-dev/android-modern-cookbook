package com.dhruva.paginationsearchbackground.data.remote.repository

import androidx.paging.PagingData
import com.dhruva.paginationsearchbackground.data.model.RepositoryItem
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>>
    fun searchRepositoriesOffline(query: String): Flow<PagingData<RepositoryItem>>
    fun searchRepositoriesCombined(query: String): Flow<PagingData<RepositoryItem>>
}
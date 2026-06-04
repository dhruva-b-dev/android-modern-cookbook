package com.dhruva.paginationsearchbackground.data.remote.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dhruva.paginationsearchbackground.data.local.database.AppDatabase
import com.dhruva.paginationsearchbackground.data.model.RepositoryItem
import com.dhruva.paginationsearchbackground.data.paging.GitHubPagingSource
import com.dhruva.paginationsearchbackground.data.remote.api.GithubApiService
import com.dhruva.paginationsearchbackground.util.NetworkUtils
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService,
    private val appDatabase: AppDatabase,
    private val networkUtils: NetworkUtils
) :GitHubRepository{

    override fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { GitHubPagingSource(apiService, appDatabase, query) }
        ).flow
    }

    override fun searchRepositoriesOffline(query: String): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { appDatabase.repositoryDao().searchRepository(query) }
        ).flow
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun searchRepositoriesCombined(query: String): Flow<PagingData<RepositoryItem>> {
        return if (networkUtils.isNetworkAvailable()) {
            searchRepositories(query)
        } else {
            searchRepositoriesOffline(query)
        }
    }

}
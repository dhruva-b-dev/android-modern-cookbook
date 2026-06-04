package com.dhruva.paginationsearchbackground.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dhruva.paginationsearchbackground.data.local.database.AppDatabase
import com.dhruva.paginationsearchbackground.data.model.RepositoryItem
import com.dhruva.paginationsearchbackground.data.remote.api.GithubApiService

class GitHubPagingSource(
    private val githubApiService: GithubApiService,
    private val appDatabase: AppDatabase,
    private val query: String
) : PagingSource<Int, RepositoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        val page = params.key ?: 1
        return try {
            val response = githubApiService.searchRepositories(query, page, params.loadSize)
            val items = response.items

            // Only clear and save to DB on the FIRST page (Refresh)
            if (params is LoadParams.Refresh) {
                appDatabase.repositoryDao().deleteAllRepositories()
                // Save a snapshot of the first page
                val limitedItems = items.take(15)
                appDatabase.repositoryDao().insertRepositories(limitedItems)
            }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
        return state.anchorPosition
    }
}
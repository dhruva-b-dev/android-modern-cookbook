package com.dhruva.paginationsearchbackground.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dhruva.paginationsearchbackground.data.local.database.AppDatabase
import com.dhruva.paginationsearchbackground.data.remote.api.GithubApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val apiService: GithubApiService,
    private val database: AppDatabase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Fetch some default data in the background (e.g., "Android" repositories)
            val response = apiService.searchRepositories("Android", 1, 20)
            
            if (response.items.isNotEmpty()) {
                // In a real app, you might want to be more surgical with updates 
                // but for this example, we'll sync the latest results
                database.repositoryDao().deleteAllRepositories()
                database.repositoryDao().insertRepositories(response.items)
            }
            
            Result.success()
        } catch (e: Exception) {
            // If it fails (e.g., network issue), WorkManager can retry later
            Result.retry()
        }
    }
}

package com.dhruva.paginationsearchbackground.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhruva.paginationsearchbackground.data.model.RepositoryItem

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryItem>)

    @Query("select * from repositories")
    fun getRepositories(): PagingSource<Int, RepositoryItem>

    @Query("select * from repositories where name like '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchRepository(query: String): PagingSource<Int, RepositoryItem>

    @Query("delete from repositories")
    suspend fun deleteAllRepositories()
}
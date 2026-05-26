package com.dhruva.kotlincoroutines.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dhruva.kotlincoroutines.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun insertAll(users: List<UserEntity>)

    @Delete
    suspend fun delete(user: UserEntity)

}
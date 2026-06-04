package com.dhruva.paginationsearchbackground.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dhruva.paginationsearchbackground.data.local.dao.RepositoryDao
import com.dhruva.paginationsearchbackground.data.model.RepositoryItem

@Database(entities = [RepositoryItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun repositoryDao(): RepositoryDao
}
package com.motilal.githubrepository.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.motilal.githubrepository.db.dao.ReposDao

import com.motilal.githubrepository.db.entity.covertor.OwnerTypeConverter
import com.motilal.githubrepository.trending.data.model.Repo

@Database(entities = [Repo::class], version = 1,exportSchema = false)
@TypeConverters(OwnerTypeConverter::class)
abstract class GitHubDatabase : RoomDatabase(){
    abstract fun reposDao(): ReposDao

    companion object {
        val DATABASE_NAME = "my_db"
    }
}
package com.motilal.githubrepository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.motilal.githubrepository.trending.data.model.Repo


import kotlinx.coroutines.flow.Flow
@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(githubEntities: List<Repo>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories1(githubEntities: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubEntities:Repo)

    @Query("SELECT * FROM `repositories` where id = :page")
    fun getRepositoriesByPage(page: Long?): List<Repo?>?

    @Query("SELECT * FROM `repositories`")
    fun getRepositories(): List<Repo>
    @Query("SELECT * FROM `repositories` LIMIT :limit OFFSET :offset")
    fun getReposByLimits(limit:Int,offset : Int) : List<Repo>

    @Query("SELECT COUNT('id') FROM `repositories`")
    fun getRepoCount() : Int

}
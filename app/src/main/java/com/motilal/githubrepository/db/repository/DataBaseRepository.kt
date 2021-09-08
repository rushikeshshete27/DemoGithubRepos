package com.motilal.githubrepository.db.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.motilal.githubrepository.db.dao.ReposDao
import com.motilal.githubrepository.db.entity.covertor.ReposPagingSource
import com.motilal.githubrepository.trending.data.GithubPagingSource
import com.motilal.githubrepository.trending.data.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseRepository @Inject constructor(private val reposDao: ReposDao) {

/*
    @WorkerThread
    suspend fun insertRepositories() : List<Repo> = withContext(Dispatchers.IO){
       return null;
    }
*/

    @WorkerThread
    suspend fun insertRepositories(repoList : List<Repo>) {
         reposDao.insertRepositories(repoList)
    }

    suspend fun getRepositories() : List<Repo> = withContext(Dispatchers.IO) {
        reposDao.getRepositories()
    }

    suspend fun getRepositories(limit : Int,offset : Int) : List<Repo> = withContext(Dispatchers.IO) {
        reposDao.getReposByLimits(limit,offset)
    }

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReposPagingSource(reposDao, query) }
        ).liveData


}
package com.motilal.githubrepository.trending.data

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.motilal.githubrepository.trending.api.GithubApi
import com.motilal.githubrepository.trending.data.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(private val githubApi: GithubApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query) }
        ).liveData

    @WorkerThread
    suspend fun fetchRepositories() : List<Repo> = withContext(Dispatchers.IO){
        githubApi.getTrendingRepos("language:Kotlin",1,100).items
    }

}
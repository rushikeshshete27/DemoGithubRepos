package com.motilal.githubrepository.trending.data

import androidx.annotation.WorkerThread
import com.motilal.githubrepository.utils.Utils
import com.motilal.githubrepository.trending.api.GithubApi
import com.motilal.githubrepository.trending.data.model.Repo
import com.motilal.githubrepository.utils.AppPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(private val githubApi: GithubApi) {
    @Inject lateinit var appPreference: AppPreference
    @WorkerThread
    suspend fun fetchRepositories() : List<Repo> = withContext(Dispatchers.IO){
        val page = appPreference.getNextPage();
        val response =  githubApi.getTrendingRepos("language:Kotlin",page,100)
        appPreference.setSavePage(page)
        response.items
    }



}
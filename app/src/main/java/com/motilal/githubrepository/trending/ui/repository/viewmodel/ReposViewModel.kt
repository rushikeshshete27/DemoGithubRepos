package com.motilal.githubrepository.trending.ui.repository.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.motilal.githubrepository.utils.Utils
import com.motilal.githubrepository.db.repository.DataBaseRepository
import com.motilal.githubrepository.trending.data.GithubRepository
import com.motilal.githubrepository.trending.data.model.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.internal.wait
import javax.inject.Inject
@HiltViewModel
class ReposViewModel @Inject constructor (private val gitRepository: GithubRepository, private val dataBaseRepository: DataBaseRepository) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val repos = currentQuery.switchMap { queryString ->

      runBlocking {
          launch {
              storeData()
          }
      }

        dataBaseRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    @WorkerThread
    suspend fun storeData() : Unit =  withContext(Dispatchers.IO) {
        if (Utils.isInternetAvailable()) {
            var listRepos = gitRepository.fetchRepositories()
            if(!listRepos.isEmpty())
            dataBaseRepository.insertRepositories(listRepos)
        }

    }



    companion object {
        private const val DEFAULT_QUERY = "language:Kotlin"
    }
}
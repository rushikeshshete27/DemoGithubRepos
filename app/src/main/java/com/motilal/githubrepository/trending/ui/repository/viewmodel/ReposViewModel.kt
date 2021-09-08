package com.motilal.githubrepository.trending.ui.repository.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.motilal.githubrepository.db.repository.DataBaseRepository
import com.motilal.githubrepository.trending.data.GithubRepository

class ReposViewModel @ViewModelInject constructor (private val repository: GithubRepository,private val dataBaseRepository: DataBaseRepository) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

  /*  val repos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }*/




    val repos = currentQuery.switchMap { queryString ->
        dataBaseRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }




    fun searchRepos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "language:Kotlin"
    }
}
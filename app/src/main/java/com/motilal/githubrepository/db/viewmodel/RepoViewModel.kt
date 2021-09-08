package com.motilal.githubrepository.db.viewmodel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*

import com.motilal.githubrepository.trending.data.GithubRepository
import com.motilal.githubrepository.trending.data.model.Repo
import com.motilal.githubrepository.trending.ui.repository.viewmodel.ReposViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.annotation.Resource
import androidx.lifecycle.liveData as livedata1

class RepoViewModel @ViewModelInject constructor(private val repository: GithubRepository) : ViewModel() {

  /*  fun getData = androidx.lifecycle.livedata1(Dispatchers.IO){

    }*/

}
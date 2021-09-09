package com.motilal.githubrepository.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.motilal.githubrepository.db.repository.DataBaseRepository
import com.motilal.githubrepository.trending.data.GithubRepository

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class ApiWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    @Inject lateinit var dataBaseRepository: DataBaseRepository
    @Inject lateinit var githubRepository : GithubRepository
    val context = context
    override suspend fun doWork(): Result {
        //repository.insertRepositories()
        downloadAndStoreData()
        testing(context)
        return Result.retry()
    }

    fun testing(context: Context) {
        var shared = context.getSharedPreferences("gitdb", Context.MODE_PRIVATE)
        var total = shared.getInt("total", 0)
        val edit = shared.edit()
        total = total + 15
        edit.putInt("total", total)
        edit.apply()
    }

    suspend fun downloadAndStoreData(){
        val response = githubRepository.fetchRepositories();
        //Log.d("Response",""+response.get(0).description)
        dataBaseRepository.insertRepositories(response)
        val listRepos = dataBaseRepository.getRepositories()
        //Log.d("Response",""+listRepos.get(1).description)
    }


}
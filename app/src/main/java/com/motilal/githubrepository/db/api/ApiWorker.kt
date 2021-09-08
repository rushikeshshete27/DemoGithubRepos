package com.motilal.githubrepository.db.api

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.motilal.githubrepository.services.ForegroundService


/*class ApiWorker @WorkerInject constructor(@Assisted context: Context, @Assisted workerParams: WorkerParameters, @Assisted  private val repository: TrendingRepository) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
       // TODO("Not yet implemented")
        repository.insertRepositories()
        return Result.success()
    }*/

class ApiWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    var context = context;
    override suspend fun doWork(): Result {
        // TODO("Not yet implemented")
        ForegroundService.startService(context.applicationContext,"Forground Service Start")
        var shared = context.getSharedPreferences("gitdb" , Context.MODE_PRIVATE)
        var total = shared.getInt("total",0)
        val edit = shared.edit()
        total = total + 15
        edit.putInt("total" ,total)
        edit.apply()

        return Result.retry()
    }

}
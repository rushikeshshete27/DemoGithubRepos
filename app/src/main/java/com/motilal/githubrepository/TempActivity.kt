package com.motilal.githubrepository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.motilal.githubrepository.databinding.ActivityTemp2Binding
import com.motilal.githubrepository.worker.ApiWorker
import com.motilal.githubrepository.services.ForegroundService
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class TempActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTemp2Binding
//    private val storeDataViewModel: TrendingRepository by viewModels()
    val TAG_MY_WORK = "mywork"
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener(View.OnClickListener {
            ForegroundService.startService(this, "Foreground Service is running...")
        })
        binding.buttonStop.setOnClickListener(View.OnClickListener {
            ForegroundService.stopService(this)
        })

      //  scheduleWork(TAG_MY_WORK);


        testWorker()


    }



    fun scheduleWork(tag: String?) {
        val periodicWork = PeriodicWorkRequest.Builder(ApiWorker::class.java, 2, TimeUnit.MINUTES,2,TimeUnit.MINUTES)
        val request = periodicWork.build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(tag!!, ExistingPeriodicWorkPolicy.KEEP, request)
    }

    fun testWorker() {
        var shared = applicationContext.getSharedPreferences("gitdb", Context.MODE_PRIVATE)
        var total = shared.getInt("total", 0)
        binding.tvTotal.setText(""+total)

    }
}
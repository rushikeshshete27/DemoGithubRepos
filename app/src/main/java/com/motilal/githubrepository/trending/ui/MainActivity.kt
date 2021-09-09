package com.motilal.githubrepository.trending.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.motilal.githubrepository.R
import com.motilal.githubrepository.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG_MY_WORK = "mywork"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // ForegroundService.startService(this,"Foreground Service is running")
        Utils.checkAndScheduleWorker(this.applicationContext,TAG_MY_WORK)


    }




}


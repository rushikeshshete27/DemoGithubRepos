package com.motilal.githubrepository

import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GitHubApplication : MultiDexApplication(),Configuration.Provider {
    //@Inject
    //lateinit var workerFactory: HiltWorkerFactory


    override fun getWorkManagerConfiguration(): Configuration {
        TODO("Not yet implemented")
    }

    /*   @Inject
       lateinit var workerFactory: HiltWorkerFactory

       override fun getWorkManagerConfiguration(): Configuration {
           return Configuration.Builder()
               .setWorkerFactory(workerFactory)
               .build()
       }*/

}
package com.motilal.githubrepository.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.motilal.githubrepository.GitHubApplication
import com.motilal.githubrepository.worker.ApiWorker
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit


class Utils {

    companion object {

        fun checkAndScheduleWorker(context: Context, tag: String?) {
            if (tag?.let { isWorkScheduled(context, it) } == false) {
                scheduleWork(context, tag)
            }

        }

        fun isWorkScheduled(context: Context, tag: String): Boolean {
            val instance = WorkManager.getInstance(context.applicationContext)
            val statuses = instance.getWorkInfosByTag(tag)
            return try {
                var running = false
                val workInfoList = statuses.get()
                for (workInfo in workInfoList) {
                    val state = workInfo.state
                    if (state == WorkInfo.State.RUNNING || state == WorkInfo.State.ENQUEUED)
                        running = true
                }
                running
            } catch (e: ExecutionException) {
                e.printStackTrace()
                false
            } catch (e: InterruptedException) {
                e.printStackTrace()
                false
            }
        }


        fun scheduleWork(context: Context, tag: String?) {
            val periodicWork = PeriodicWorkRequest.Builder(
                ApiWorker::class.java,
                15,
                TimeUnit.MINUTES,
                5,
                TimeUnit.MINUTES
            )
            val request = periodicWork.build()
            WorkManager.getInstance(context.applicationContext)
                .enqueueUniquePeriodicWork(tag!!, ExistingPeriodicWorkPolicy.KEEP, request)
        }

        fun isInternetAvailable(): Boolean {
            try {
                val address: InetAddress = InetAddress.getByName("www.google.com")
                return !address.equals("")
            } catch (e: UnknownHostException) {
                // Log error
            }
            return false
        }

    }



}
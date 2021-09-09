package com.motilal.githubrepository.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.motilal.githubrepository.R
import com.motilal.githubrepository.db.repository.DataBaseRepository
import com.motilal.githubrepository.trending.data.GithubRepository
import com.motilal.githubrepository.trending.ui.MainActivity
import com.motilal.githubrepository.trending.ui.repository.viewmodel.ReposViewModel
import com.motilal.githubrepository.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
@AndroidEntryPoint
class ForegroundService : Service() {
    private val CHANNEL_ID = "ForegroundService Kotlin"
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var githubRepository: GithubRepository

    @Inject
    lateinit var dataBaseRepository: DataBaseRepository

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        downloadAndStoreData()

        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        //stopSelf();
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun downloadAndStoreData() {
        scope.launch {
            if(Utils.isInternetAvailable()) {
                val response = githubRepository.fetchRepositories();
               // Log.d("Response", "" + response.get(0).description)
                if(response!=null && response.size >0)
                dataBaseRepository.insertRepositories(response)
               // val listRepos = dataBaseRepository.getRepositories()
               // Log.d("Response", "" + listRepos.get(1).description)
                stopService(application)
            }
        }
    }
}

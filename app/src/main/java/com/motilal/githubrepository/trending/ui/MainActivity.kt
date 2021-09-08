package com.motilal.githubrepository.trending.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.motilal.githubrepository.R
import com.motilal.githubrepository.db.api.ApiWorker
import com.motilal.githubrepository.receivers.AlarmReceiver
import com.motilal.githubrepository.services.ForegroundService
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // if(!checkAlarmWorking())
        //scheduleWork("startservice")
        setNewAlaram()
        setAlarm()

    }


    fun checkAlarmWorking() : Boolean{

        val intent = Intent(this, AlarmReceiver::class.java) //the same as up
        intent.action = "ALARAM_RECEIVE" //the same as up

        val isWorking = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            ) != null //just changed the flag

        Log.d("Response", "alarm is " + (if (isWorking) "" else "not") + " working...")
        return isWorking



    }

    fun setNewAlaram() {
        var alarmMgr: AlarmManager? = null
        var alarmIntent: PendingIntent

        alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            intent.action="ALARAM_RECEIVE";
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }
        val rightNow = Calendar.getInstance()
        val currentHourIn24Format = rightNow[Calendar.HOUR_OF_DAY] // return the hour in 24 hrs format (ranging from 0-23)
        val currentHourIn12Format = rightNow[Calendar.HOUR]
        val currenMinute = rightNow[Calendar.MINUTE]
// Set the alarm to start at 8:30 a.m.
        val calendar: Calendar = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY,currentHourIn24Format)
            set(Calendar.MINUTE, currenMinute+1)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis+1000,alarmIntent)
        }else {
            alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 2,
                alarmIntent
            )
        }

    }

    fun setAlarm() {
        //If the PendingIntent was created with FLAG_ONE_SHOT, it cannot be canceled.
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.action="ALARAM_RECEIVE";
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)


            // Hopefully your alarm will have a lower frequency than this!
            alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent
            )
        }
    }


    fun scheduleWork(tag: String?) {
        val periodicWork = PeriodicWorkRequest.Builder(
            ApiWorker::class.java, 15, TimeUnit.MINUTES,15,
            TimeUnit.MINUTES)
        val request = periodicWork.build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(tag!!, ExistingPeriodicWorkPolicy.KEEP, request)
    }


}


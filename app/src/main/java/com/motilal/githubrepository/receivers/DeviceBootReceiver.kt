package com.motilal.githubrepository.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.motilal.githubrepository.services.ForegroundService

class DeviceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
        if (context != null) {
            ForegroundService.startService(context.applicationContext, "Foreground Service is running...")
        }
    }
}
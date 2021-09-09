package com.motilal.githubrepository.utils

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(@ApplicationContext context : Context){
    val prefs = context.getSharedPreferences("gitdb", Context.MODE_PRIVATE)
    val  PREF_TAG_PAGENO= "page_no"
    fun getNextPage(): Int {
        return prefs.getInt(PREF_TAG_PAGENO, 0)+1
    }
    fun setSavePage(pageNo: Int) {
        prefs.edit().putInt(PREF_TAG_PAGENO, pageNo).apply()
    }
}
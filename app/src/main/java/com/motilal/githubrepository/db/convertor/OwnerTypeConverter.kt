package com.motilal.githubrepository.db.convertor

import androidx.room.TypeConverter
import com.motilal.githubrepository.trending.data.model.Owner
import org.json.JSONObject


object OwnerTypeConverter {
    @TypeConverter
    fun fromSource(source: Owner): String {
        return JSONObject().apply {
            put("id", source.id)
            put("login", source.login)
            put("avatar_url", source.avatar_url)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): Owner {
        val json = JSONObject(source)
        return Owner(json.getInt("id"), json.getString("login"),json.getString("avatar_url"))
    }
}
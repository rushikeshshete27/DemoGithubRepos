package com.motilal.githubrepository.trending.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
data class Owner(
    @NonNull
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String
): Parcelable
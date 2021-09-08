package com.motilal.githubrepository.db.di

import android.content.Context
import androidx.room.Room
import com.motilal.githubrepository.db.database.GitHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        GitHubDatabase::class.java,
        GitHubDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: GitHubDatabase) = db.reposDao() // The reason we can implement a Dao for the database

}
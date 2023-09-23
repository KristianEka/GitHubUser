package com.ekachandra.githubuser.core.di

import android.content.Context
import androidx.room.Room
import com.ekachandra.githubuser.core.data.source.local.room.UserDao
import com.ekachandra.githubuser.core.data.source.local.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "Users.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
}
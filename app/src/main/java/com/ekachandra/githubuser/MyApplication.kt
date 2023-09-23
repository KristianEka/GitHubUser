package com.ekachandra.githubuser

import android.app.Application
import com.ekachandra.githubuser.core.di.databaseModule
import com.ekachandra.githubuser.core.di.networkModule
import com.ekachandra.githubuser.core.di.repositoryModule
import com.ekachandra.githubuser.di.useCaseModule
import com.ekachandra.githubuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
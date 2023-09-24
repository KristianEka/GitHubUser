package com.ekachandra.githubuser.core.di

import androidx.room.Room
import com.ekachandra.githubuser.core.BuildConfig
import com.ekachandra.githubuser.core.data.UserRepository
import com.ekachandra.githubuser.core.data.source.local.LocalDataSource
import com.ekachandra.githubuser.core.data.source.local.preferences.SharedPreferences
import com.ekachandra.githubuser.core.data.source.local.room.UserDatabase
import com.ekachandra.githubuser.core.data.source.remote.RemoteDataSource
import com.ekachandra.githubuser.core.data.source.remote.network.ApiService
import com.ekachandra.githubuser.core.domain.repository.IUserRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "Users.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "token " + BuildConfig.KEY)
                .build()
            chain.proceed(requestHeaders)
        }

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { SharedPreferences(get()) }
    single<IUserRepository> {
        UserRepository(
            get(),
            get(),
            get()
        )
    }
}
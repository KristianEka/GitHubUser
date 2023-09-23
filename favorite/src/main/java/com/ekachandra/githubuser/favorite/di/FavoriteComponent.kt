package com.ekachandra.githubuser.favorite.di

import android.content.Context
import com.ekachandra.githubuser.di.FavoriteModuleDependencies
import com.ekachandra.githubuser.favorite.presentation.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
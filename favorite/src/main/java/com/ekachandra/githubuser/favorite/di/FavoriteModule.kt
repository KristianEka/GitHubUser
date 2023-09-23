package com.ekachandra.githubuser.favorite.di

import com.ekachandra.githubuser.favorite.presentation.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}
package com.ekachandra.githubuser.di

import com.ekachandra.githubuser.core.domain.usecase.UserUseCase
import com.ekachandra.githubuser.core.domain.usecase.UsersInteractor
import com.ekachandra.githubuser.presentation.detail.DetailViewModel
import com.ekachandra.githubuser.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UsersInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
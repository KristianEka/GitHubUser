package com.ekachandra.githubuser.di

import com.ekachandra.githubuser.core.domain.usecase.UserUseCase
import com.ekachandra.githubuser.core.domain.usecase.UsersInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideUserUseCase(usersInteractor: UsersInteractor): UserUseCase
}
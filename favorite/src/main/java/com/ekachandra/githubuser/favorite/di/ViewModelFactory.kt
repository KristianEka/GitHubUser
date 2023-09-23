package com.ekachandra.githubuser.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekachandra.githubuser.core.domain.usecase.UserUseCase
import com.ekachandra.githubuser.favorite.presentation.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(userUseCase) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}
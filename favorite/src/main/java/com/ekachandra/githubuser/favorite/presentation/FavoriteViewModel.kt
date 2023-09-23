package com.ekachandra.githubuser.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekachandra.githubuser.core.domain.usecase.UserUseCase

class FavoriteViewModel (
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getAllUserFavorite() = userUseCase.getAllUserFavorite().asLiveData()
}
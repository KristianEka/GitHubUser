package com.ekachandra.githubuser.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekachandra.githubuser.core.domain.usecase.UserUseCase

class MainViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getUsersByUsername(username: String) = userUseCase.getUsersByUsername(username).asLiveData()
}
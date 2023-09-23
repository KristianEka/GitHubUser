package com.ekachandra.githubuser.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekachandra.githubuser.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getUsersByUsername(username: String) = userUseCase.getUsersByUsername(username).asLiveData()
}
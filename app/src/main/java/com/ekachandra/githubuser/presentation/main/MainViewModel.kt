package com.ekachandra.githubuser.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekachandra.githubuser.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getUsersByUsername(username: String) = userUseCase.getUsersByUsername(username).asLiveData()

    fun getThemeSetting() = userUseCase.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActivity: Boolean) {
        viewModelScope.launch {
            userUseCase.saveThemeSetting(isDarkModeActivity)
        }
    }
}
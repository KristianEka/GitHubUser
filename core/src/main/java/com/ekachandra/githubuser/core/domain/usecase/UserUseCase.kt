package com.ekachandra.githubuser.core.domain.usecase

import com.ekachandra.githubuser.core.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun getUsersByUsername(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>>

    fun getUserDetail(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<Users>>

    fun getUserFollowers(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>>

    fun getUserFollowing(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>>

    fun getAllUserFavorite(): Flow<List<Users>>

    suspend fun insertUserFavorite(users: Users)

    suspend fun deleteUserFavorite(users: Users)

    fun getFavoriteIsExists(username: String): Flow<Boolean>

    fun getThemeSetting(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}
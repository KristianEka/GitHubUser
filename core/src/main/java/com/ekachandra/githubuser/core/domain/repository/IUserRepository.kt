package com.ekachandra.githubuser.core.domain.repository

import com.ekachandra.githubuser.core.data.Resource
import com.ekachandra.githubuser.core.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getUsersByUsername(username: String): Flow<Resource<List<Users>>>

    fun getUserDetail(username: String): Flow<Resource<Users>>

    fun getUserFollowers(username: String): Flow<Resource<List<Users>>>

    fun getUserFollowing(username: String): Flow<Resource<List<Users>>>

    fun getAllUserFavorite(): Flow<List<Users>>

    suspend fun insertUserFavorite(users: Users)

    suspend fun deleteUserFavorite(users: Users)

    fun getFavoriteIsExists(username: String): Flow<Boolean>
}
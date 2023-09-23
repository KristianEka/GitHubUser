package com.ekachandra.githubuser.core.data.source.local

import com.ekachandra.githubuser.core.data.source.local.entity.UserEntity
import com.ekachandra.githubuser.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val userDao: UserDao,
) {
    fun getAllUserFavorite(): Flow<List<UserEntity>> = userDao.getAllUserFavorite()

    suspend fun insertUserFavorite(user: UserEntity) = userDao.insertUserFavorite(user)

    suspend fun deleteUserFavorite(user: UserEntity) = userDao.deleteUserFavorite(user)

    fun getFavoriteIsExists(username: String): Flow<Boolean> = userDao.getFavoriteIsExists(username)
}
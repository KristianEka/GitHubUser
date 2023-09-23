package com.ekachandra.githubuser.core.data.source.local

import com.ekachandra.githubuser.core.data.source.local.entity.UserEntity
import com.ekachandra.githubuser.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
) {
    fun getAllUserFavorite(): Flow<List<UserEntity>> = userDao.getAllUserFavorite()

    suspend fun insertUserFavorite(user: UserEntity) = userDao.insertUserFavorite(user)

    suspend fun deleteUserFavorite(user: UserEntity) = userDao.deleteUserFavorite(user)

    fun getFavoriteIsExists(username: String): Flow<Boolean> = userDao.getFavoriteIsExists(username)
}
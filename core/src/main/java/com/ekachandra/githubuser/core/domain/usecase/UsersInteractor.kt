package com.ekachandra.githubuser.core.domain.usecase

import com.ekachandra.githubuser.core.domain.model.Users
import com.ekachandra.githubuser.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UsersInteractor(
    private val userRepository: IUserRepository,
) : UserUseCase {
    override fun getUsersByUsername(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>> =
        userRepository.getUsersByUsername(username)

    override fun getUserDetail(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<Users>> =
        userRepository.getUserDetail(username)

    override fun getUserFollowers(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>> =
        userRepository.getUserFollowers(username)

    override fun getUserFollowing(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>> =
        userRepository.getUserFollowing(username)

    override fun getAllUserFavorite(): Flow<List<Users>> =
        userRepository.getAllUserFavorite()

    override suspend fun insertUserFavorite(users: Users) =
        userRepository.insertUserFavorite(users)

    override suspend fun deleteUserFavorite(users: Users) =
        userRepository.deleteUserFavorite(users)

    override fun getFavoriteIsExists(username: String): Flow<Boolean> =
        userRepository.getFavoriteIsExists(username)

}
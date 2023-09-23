package com.ekachandra.githubuser.core.data

import com.ekachandra.githubuser.core.data.source.local.LocalDataSource
import com.ekachandra.githubuser.core.data.source.remote.RemoteDataSource
import com.ekachandra.githubuser.core.data.source.remote.network.ApiResponse
import com.ekachandra.githubuser.core.data.source.remote.response.UserResponse
import com.ekachandra.githubuser.core.domain.model.Users
import com.ekachandra.githubuser.core.domain.repository.IUserRepository
import com.ekachandra.githubuser.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IUserRepository {

    override fun getUsersByUsername(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>> =
        object : com.ekachandra.githubuser.core.data.NetworkBoundResource<List<Users>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getUsersByUsername(username)

        }.asFlow()

    override fun getUserDetail(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<Users>> =
        object : com.ekachandra.githubuser.core.data.NetworkBoundResource<Users, UserResponse>() {
            override fun loadFromNetwork(data: UserResponse): Flow<Users> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteDataSource.getUserDetail(username)

        }.asFlow()


    override fun getUserFollowers(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>> =
        object : com.ekachandra.githubuser.core.data.NetworkBoundResource<List<Users>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getUserFollowers(username)

        }.asFlow()

    override fun getUserFollowing(username: String): Flow<com.ekachandra.githubuser.core.data.Resource<List<Users>>> =
        object : com.ekachandra.githubuser.core.data.NetworkBoundResource<List<Users>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getUserFollowing(username)

        }.asFlow()

    override fun getAllUserFavorite(): Flow<List<Users>> {
        return localDataSource.getAllUserFavorite().map { userEntities ->
            userEntities.let {
                DataMapper.mapEntitiesToDomain(it)
            }
        }
    }

    override suspend fun insertUserFavorite(users: Users) {
        localDataSource.insertUserFavorite(DataMapper.mapDomainToEntity(users))
    }

    override suspend fun deleteUserFavorite(users: Users) {
        localDataSource.deleteUserFavorite(DataMapper.mapDomainToEntity(users))
    }

    override fun getFavoriteIsExists(username: String): Flow<Boolean> =
        localDataSource.getFavoriteIsExists(username)

}
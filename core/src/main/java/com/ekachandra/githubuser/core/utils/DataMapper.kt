package com.ekachandra.githubuser.core.utils

import com.ekachandra.githubuser.core.data.source.local.entity.UserEntity
import com.ekachandra.githubuser.core.data.source.remote.response.UserResponse
import com.ekachandra.githubuser.core.domain.model.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapDomainToEntity(input: Users) = UserEntity(
        login = input.login,
        name = input.name,
        avatarUrl = input.avatarUrl,
        followers = input.followers,
        following = input.following,
    )

    fun mapResponsesToDomain(input: List<UserResponse>): Flow<List<Users>> {
        val userList = ArrayList<Users>()
        input.map {
            val user = Users(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                following = it.following,
            )
            userList.add(user)
        }
        return flowOf(userList)
    }

    fun mapResponsesToDomain(input: UserResponse): Flow<Users> {
        return flowOf(
            Users(
                login = input.login,
                name = input.name,
                avatarUrl = input.avatarUrl,
                followers = input.followers,
                following = input.following,
            )
        )
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<Users> =
        input.map {
            Users(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                following = it.following,
            )
        }
}
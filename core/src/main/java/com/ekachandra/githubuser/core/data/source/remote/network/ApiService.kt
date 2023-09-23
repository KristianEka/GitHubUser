package com.ekachandra.githubuser.core.data.source.remote.network

import com.ekachandra.githubuser.core.data.source.remote.response.GithubResponse
import com.ekachandra.githubuser.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getUserByUsername(
        @Query("q") username: String,
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String,
    ): UserResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
    ): List<UserResponse>
}
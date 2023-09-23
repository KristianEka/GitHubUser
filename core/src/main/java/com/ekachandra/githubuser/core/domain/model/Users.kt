package com.ekachandra.githubuser.core.domain.model

data class Users(
    val login: String,
    val name: String?,
    val avatarUrl: String?,
    val followers: Int?,
    val following: Int?,
)

package com.ekachandra.githubuser.core.data.source.remote.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

class UserResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "followers")
    val followers: Int? = null,

    @ColumnInfo(name = "following")
    val following: Int? = null,
)

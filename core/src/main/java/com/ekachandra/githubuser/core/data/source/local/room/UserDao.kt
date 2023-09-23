package com.ekachandra.githubuser.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ekachandra.githubuser.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUserFavorite(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFavorite(user: UserEntity)

    @Delete
    suspend fun deleteUserFavorite(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    fun getFavoriteIsExists(username: String): Flow<Boolean>
}
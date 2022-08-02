package com.example.appcattrilha.db.dao

import androidx.room.*
import com.example.luastation.db.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity?

    @Query("SELECT id FROM user WHERE username = :username and password = :password")
    suspend fun login(username: String, password: String): Long?
}

package com.example.luastation.db.repository

import com.example.luastation.db.model.User
import com.example.luastation.db.registration.RegistrationViewParams

interface UserRepository {

    suspend fun createUser(registrationViewParams: RegistrationViewParams)

    suspend fun getUser(id: Long) : User?

    suspend fun login (username: String, password: String): Long?
}
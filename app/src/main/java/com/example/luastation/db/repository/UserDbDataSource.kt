package com.example.luastation.db.repository

import com.example.appcattrilha.db.dao.UserDao
import com.example.luastation.db.model.User
import com.example.luastation.db.registration.RegistrationViewParams
import com.example.luastation.db.toUser
import com.example.luastation.db.toUserEntity

class UserDbDataSource(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun createUser(registrationViewParams: RegistrationViewParams) {
        val userEntity = registrationViewParams.toUserEntity()
        userDao.save(userEntity)
    }

    override suspend fun getUser(id: Long): User? {
        return userDao.getUser(id)?.toUser()
    }

    override suspend fun login(username: String, password: String): Long? {
        return userDao.login(username, password)
    }
}

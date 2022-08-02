package com.example.luastation.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.luastation.db.model.User
import com.example.luastation.db.registration.RegistrationViewParams

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val social_name: String,
    val email: String,
    val password: String
)

fun RegistrationViewParams.toUserEntity(): UserEntity {
    return with(this) {
        UserEntity(
            social_name = this.social_name,
            username = this.username,
            email = this.email,
            password = this.password
        )
    }
}

fun UserEntity.toUser(): User {
    return User(
        id = this.id.toString(),
        social_name = this.social_name,
        username = this.username,
        email = this.email
    )
}

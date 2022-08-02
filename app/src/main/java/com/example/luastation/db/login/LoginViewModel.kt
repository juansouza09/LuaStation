package com.example.luastation.db.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.luastation.db.repository.UserRepository

class LoginViewModel(
    val userRepository: UserRepository
) : ViewModel() {

    class LoginViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(userRepository) as T
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        val userId = userRepository.login(username, password)
        return userId != null
    }
}

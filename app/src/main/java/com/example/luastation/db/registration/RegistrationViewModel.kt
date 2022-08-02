package com.example.luastation.db.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.luastation.db.repository.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(
    val userRepository: UserRepository
) : ViewModel() {

    class RegistrationViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegistrationViewModel(userRepository) as T
        }
    }

    fun createUser(social_name: String, email: String, username: String, password: String) {
        viewModelScope.launch {
            userRepository.createUser(
                RegistrationViewParams(
                    social_name,
                    username,
                    email,
                    password
                )
            )
        }
    }

    fun isValidProfileData(
        social_name: String,
        email: String,
        username: String,
        password: String
    ): Boolean {
        return when {
            social_name.isEmpty() ||
                email.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty() -> false
            else -> true
        }
    }
}

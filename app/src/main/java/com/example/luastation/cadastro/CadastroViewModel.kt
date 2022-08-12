package com.example.luastation.cadastro

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference

class CadastroViewModel : ViewModel() {
    private lateinit var dbRef: DatabaseReference



    fun isValidProfileData(
        name: String,
        email: String,
        password: String,
        dataNasc: String,
        cpf: String
    ): Boolean {
        return when {
            name.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty() ||
                    dataNasc.isEmpty() ||
                    cpf.isEmpty() -> false
            else -> true
        }
    }
}
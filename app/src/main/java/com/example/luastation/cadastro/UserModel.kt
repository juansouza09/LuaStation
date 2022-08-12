package com.example.luastation.cadastro

data class UserModel(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var dataNasc: String? = null,
    val cpf: String? = null
)
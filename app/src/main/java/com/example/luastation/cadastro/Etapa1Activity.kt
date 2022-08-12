package com.example.luastation.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.CadastroScreenBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Etapa1Activity : AppCompatActivity() {
    private lateinit var binding: CadastroScreenBinding

    private var instance = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instance.child("oi").child("01").setValue("ola")

        binding.buttonProximo.setOnClickListener {
            val freelancer = Intent(this, HomeActivity::class.java)
            startActivity(freelancer)
            finish()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

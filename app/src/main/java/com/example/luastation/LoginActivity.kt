package com.example.luastation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.cadastro.EscolhaActivity
import com.example.luastation.databinding.LoginScreenBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            abrirHome()
        }

        binding.buttonCadastrar.setOnClickListener {
            abrirCadastro()
        }
    }

    fun abrirHome(){
        val home = Intent(this, HomeActivity::class.java)
        startActivity(home)
        finish()
    }

    fun abrirCadastro(){
        val cadastro = Intent(this, EscolhaActivity::class.java)
        startActivity(cadastro)
        finish()
    }
}
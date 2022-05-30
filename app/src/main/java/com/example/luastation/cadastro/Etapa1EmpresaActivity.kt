package com.example.luastation.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.LoginActivity
import com.example.luastation.R
import com.example.luastation.databinding.CadastroScreenBinding

class Etapa1EmpresaActivity : AppCompatActivity() {
    private lateinit var binding: CadastroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonProximo.setOnClickListener {
            proximo()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    fun proximo(){
        val freelancer = Intent(this, Etapa2EmpresaActivity::class.java)
        startActivity(freelancer)
        finish()
    }

    fun cancelar(){
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}
package com.example.luastation.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.LoginActivity
import com.example.luastation.R
import com.example.luastation.databinding.Cadastro2EtapaScreenBinding

class Etapa2Activity : AppCompatActivity() {
    private lateinit var binding: Cadastro2EtapaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro2EtapaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFinalizar.setOnClickListener {
            proximo()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }

        binding.icBack.setOnClickListener {
            voltar()
        }
    }

    fun proximo() {
        val freelancer = Intent(this, Etapa3Activity::class.java)
        startActivity(freelancer)
        finish()
    }

    fun voltar() {
        val voltar = Intent(this, Etapa1Activity::class.java)
        startActivity(voltar)
        finish()
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}
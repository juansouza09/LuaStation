package com.example.luastation.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.Cadastro3EtapaScreenBinding

class Etapa3Activity : AppCompatActivity() {
    private lateinit var binding: Cadastro3EtapaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro3EtapaScreenBinding.inflate(layoutInflater)
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
        val freelancer = Intent(this, HomeActivity::class.java)
        startActivity(freelancer)
        finish()
    }

    fun voltar() {
        val voltar = Intent(this, Etapa2Activity::class.java)
        startActivity(voltar)
        finish()
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

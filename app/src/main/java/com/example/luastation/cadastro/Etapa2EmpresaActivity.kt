package com.example.luastation.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.R
import com.example.luastation.databinding.Cadastro2EtapaEmpresaScreenBinding

class Etapa2EmpresaActivity : AppCompatActivity() {
    private lateinit var binding: Cadastro2EtapaEmpresaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro2EtapaEmpresaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFinalizar.setOnClickListener {
            finalizar()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }

        binding.icBack.setOnClickListener {
            voltar()
        }
    }

    fun finalizar() {
        val finalizar = Intent(this, HomeActivity::class.java)
        startActivity(finalizar)
        finish()
    }

    fun voltar() {
        val voltar = Intent(this, Etapa1EmpresaActivity::class.java)
        startActivity(voltar)
        finish()
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}
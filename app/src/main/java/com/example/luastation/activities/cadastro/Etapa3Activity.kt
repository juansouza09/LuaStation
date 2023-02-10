package com.example.luastation.activities.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.activities.ErrorSignUpActivity
import com.example.luastation.databinding.Cadastro3EtapaScreenBinding
import com.example.luastation.activities.login.LoginActivity

class Etapa3Activity : AppCompatActivity() {
    private lateinit var binding: Cadastro3EtapaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro3EtapaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFinalizar.setOnClickListener {
            validate()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }

        binding.icBack.setOnClickListener {
            voltar()
        }
    }

    private fun validate() {
        if (!binding.checkBaixaRenda.isChecked && !binding.checkLgbt.isChecked && !binding.checkMulher.isChecked && !binding.checkPreto.isChecked) {
            val intent = Intent(this, ErrorSignUpActivity::class.java)
            startActivity(intent)
        } else {
            proximo()
        }
    }

    private fun proximo() {
        val freelancer = Intent(this, EtapaFreelancerActivity::class.java)
        startActivity(freelancer)
        finish()
    }

    private fun voltar() {
        val voltar = Intent(this, EscolhaActivity::class.java)
        startActivity(voltar)
        finish()
    }

    private fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

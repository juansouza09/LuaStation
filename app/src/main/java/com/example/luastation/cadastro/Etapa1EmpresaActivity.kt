package com.example.luastation.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.CadastroEmpresaScreenBinding

class Etapa1EmpresaActivity : AppCompatActivity() {
    private lateinit var binding: CadastroEmpresaScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroEmpresaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

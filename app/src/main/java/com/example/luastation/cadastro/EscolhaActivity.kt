package com.example.luastation.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.R
import com.example.luastation.databinding.CadastroEscolhaScreenBinding

class EscolhaActivity : AppCompatActivity() {
    private lateinit var binding: CadastroEscolhaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroEscolhaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEmpresa.setOnClickListener {
            empresa()
        }

        binding.buttonFreelancer.setOnClickListener {
            freelancer()
        }

        binding.buttonCancelar.setOnClickListener {
            cancelar()
        }
    }

    fun empresa(){
        val empresa = Intent(this, Etapa1EmpresaActivity::class.java)
        startActivity(empresa)
        finish()
    }

    fun freelancer(){
        val freelancer = Intent(this, Etapa1Activity::class.java)
        startActivity(freelancer)
        finish()
    }

    fun cancelar(){
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}
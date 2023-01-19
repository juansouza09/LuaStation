package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding

class PerfilFreelancerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dadosIntent()
        listeners()
    }

    private fun dadosIntent() {
        val intent = intent

        val name = intent.getStringExtra("iName")
        val email = intent.getStringExtra("iEmail")
        val dataNasc = intent.getStringExtra("iDataNasc")
        val cpf_cnpj = intent.getStringExtra("iCpf_cnpj")

        binding.textNameContratante.text = name
        binding.perfilNameText.text = name
        binding.perfilCpfCnpjText.text = cpf_cnpj
        binding.perfilEmailText.text = email
        binding.perfilNascText.text = dataNasc
    }

    private fun listeners() {
        binding.icBack.let {
            it.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

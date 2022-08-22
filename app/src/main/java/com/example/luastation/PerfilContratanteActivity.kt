package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding

class PerfilContratanteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dadosIntent()
        listeners()
    }

    private fun dadosIntent() {
        var intent = intent
        val aName = intent.getStringExtra("iName")
        val aEmail = intent.getStringExtra("iEmail")
        val aDataNasc = intent.getStringExtra("iDataNasc")
        val aCpf = intent.getStringExtra("iCpf")

        binding.perfilNameText.text = aName
        binding.perfilEmailText.text = aEmail
        binding.perfilNascText.text = aDataNasc
        binding.perfilCpfCnpjText.text = aCpf
        binding.textNameContratante.text = aName
    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

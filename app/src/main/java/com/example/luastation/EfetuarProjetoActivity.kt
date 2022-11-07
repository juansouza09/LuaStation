package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding
import com.example.luastation.databinding.EfetuarProjetoScreenBinding

class EfetuarProjetoActivity : AppCompatActivity() {
    private lateinit var binding: EfetuarProjetoScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EfetuarProjetoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dadosIntent()
        listeners()
    }

    private fun dadosIntent() {
        var intent = intent
        val aIdService = intent.getStringExtra("iId")
        val aCreator = intent.getStringExtra("iCreator")

    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

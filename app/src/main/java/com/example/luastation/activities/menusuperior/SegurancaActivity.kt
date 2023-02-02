package com.example.luastation.activities.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.SegurancaScreenBinding

class SegurancaActivity : AppCompatActivity() {

    private val binding by lazy { SegurancaScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.icBack.let {
            it.setOnClickListener {
                voltar()
            }
        }
    }

    private fun voltar() {
        val voltar = Intent(this, MenuActivity::class.java)
        startActivity(voltar)
        finish()
    }
}

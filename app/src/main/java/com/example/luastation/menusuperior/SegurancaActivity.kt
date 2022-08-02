package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.SegurancaScreenBinding

class SegurancaActivity : AppCompatActivity() {
    private lateinit var binding: SegurancaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SegurancaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icBack.setOnClickListener {
            voltar()
        }
    }

    fun voltar() {
        val voltar = Intent(this, MenuActivity::class.java)
        startActivity(voltar)
        finish()
    }
}

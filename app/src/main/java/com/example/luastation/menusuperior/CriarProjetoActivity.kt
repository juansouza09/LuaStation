package com.example.luastation.menusuperior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.HomeActivity
import com.example.luastation.databinding.CriarProjetoScreenBinding
import com.example.luastation.databinding.SegurancaScreenBinding

class CriarProjetoActivity : AppCompatActivity() {
    private lateinit var binding: CriarProjetoScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CriarProjetoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonProximo.setOnClickListener {
            startActivity(Intent(this, CriarProjeto2Activity::class.java))
            finish()
        }

        binding.btnCancelar.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
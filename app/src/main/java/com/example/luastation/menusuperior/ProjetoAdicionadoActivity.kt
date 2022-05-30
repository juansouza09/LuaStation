package com.example.luastation.menusuperior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.HomeActivity
import com.example.luastation.databinding.CriarProjeto2ScreenBinding
import com.example.luastation.databinding.CriarProjetoScreenBinding
import com.example.luastation.databinding.ProjetoAdicionadoScreenBinding
import com.example.luastation.databinding.SegurancaScreenBinding

class ProjetoAdicionadoActivity : AppCompatActivity() {
    private lateinit var binding: ProjetoAdicionadoScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProjetoAdicionadoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltar.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
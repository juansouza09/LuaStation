package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.CriarProjeto2ScreenBinding

class CriarProjeto2Activity : AppCompatActivity() {
    private lateinit var binding: CriarProjeto2ScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CriarProjeto2ScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icBack.setOnClickListener {
            startActivity(Intent(this, CriarProjetoActivity::class.java))
            finish()
        }

        binding.buttonProximo.setOnClickListener {
            startActivity(Intent(this, PagamentoProjetoActivity::class.java))
            finish()
        }

        binding.btnCancelar.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}

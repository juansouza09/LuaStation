package com.example.luastation.menusuperior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.HomeActivity
import com.example.luastation.databinding.CriarProjeto2ScreenBinding
import com.example.luastation.databinding.CriarProjetoScreenBinding
import com.example.luastation.databinding.ProjetoPagamentoScreenBinding
import com.example.luastation.databinding.SegurancaScreenBinding

class PagamentoProjetoActivity : AppCompatActivity() {
    private lateinit var binding: ProjetoPagamentoScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProjetoPagamentoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icBack.setOnClickListener {
            startActivity(Intent(this, CriarProjeto2Activity::class.java))
            finish()
        }

        binding.buttonFinalizar.setOnClickListener {
            startActivity(Intent(this, ProjetoAdicionadoActivity::class.java))
            finish()
        }

        binding.btnCancelar.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ProjetoPagamentoScreenBinding

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

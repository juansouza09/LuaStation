package com.solutions.luastation.activities.menusuperior

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.solutionsjs.luastation.databinding.CriarProjetoScreenBinding

class CriarProjetoActivity : AppCompatActivity() {

    private val binding by lazy { CriarProjetoScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonProximo.let {
            it.setOnClickListener {
                startActivity(Intent(this, CriarProjeto2Activity::class.java))
                finish()
            }
        }

        binding.btnCancelar.let {
            it.setOnClickListener {
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
        }

        binding.btnAnexo.let {
            it.setOnClickListener {
                Toast.makeText(this, "O documento foi adicionado!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

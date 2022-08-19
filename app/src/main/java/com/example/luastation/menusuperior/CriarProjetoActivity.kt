package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.CriarProjetoScreenBinding

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

        binding.btnAnexo.setOnClickListener {
            Toast.makeText(this, "O documento foi adicionado!", Toast.LENGTH_SHORT).show()
        }
    }
}

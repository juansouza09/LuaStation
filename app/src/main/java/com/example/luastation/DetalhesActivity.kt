package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityServicoDetalhesBinding

class DetalhesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicoDetalhesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicoDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imgContratante.setOnClickListener {
            val intent = Intent(this, PerfilContratanteActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCanditadar.setOnClickListener {
            Toast.makeText(this, "Boa sorte, Astronauta!", Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.ActivityMenuSeperiorBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuSeperiorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuSeperiorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearProfileMenu.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.linearSegurancaMenu.setOnClickListener {
            startActivity(Intent(this, SegurancaActivity::class.java))
            finish()
        }

        binding.linearPubMenu.setOnClickListener {
            startActivity(Intent(this, CriarProjetoActivity::class.java))
            finish()
        }

        binding.linearSairMenu.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

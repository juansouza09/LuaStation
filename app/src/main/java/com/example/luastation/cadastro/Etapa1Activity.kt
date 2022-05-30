package com.example.luastation.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.R
import com.example.luastation.databinding.CadastroScreenBinding

class Etapa1Activity : AppCompatActivity() {
    private lateinit var binding: CadastroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
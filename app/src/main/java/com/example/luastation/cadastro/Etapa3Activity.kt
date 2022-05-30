package com.example.luastation.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.R
import com.example.luastation.databinding.Cadastro3EtapaScreenBinding

class Etapa3Activity : AppCompatActivity() {
    private lateinit var binding: Cadastro3EtapaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro3EtapaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
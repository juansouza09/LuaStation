package com.example.luastation.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.R
import com.example.luastation.databinding.Cadastro2EtapaScreenBinding

class Etapa2Activity : AppCompatActivity() {
    private lateinit var binding: Cadastro2EtapaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro2EtapaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
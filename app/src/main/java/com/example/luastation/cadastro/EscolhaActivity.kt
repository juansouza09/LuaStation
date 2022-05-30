package com.example.luastation.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.R
import com.example.luastation.databinding.CadastroEscolhaScreenBinding

class EscolhaActivity : AppCompatActivity() {
    private lateinit var binding: CadastroEscolhaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroEscolhaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
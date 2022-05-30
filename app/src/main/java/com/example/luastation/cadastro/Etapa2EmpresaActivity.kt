package com.example.luastation.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.R
import com.example.luastation.databinding.Cadastro2EtapaEmpresaScreenBinding

class Etapa2EmpresaActivity : AppCompatActivity() {
    private lateinit var binding: Cadastro2EtapaEmpresaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro2EtapaEmpresaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
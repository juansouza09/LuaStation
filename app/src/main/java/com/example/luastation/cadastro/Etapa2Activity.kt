package com.example.luastation.cadastro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.Cadastro2EtapaScreenBinding

class Etapa2Activity : AppCompatActivity() {
    private lateinit var binding: Cadastro2EtapaScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Cadastro2EtapaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFinalizar.setOnClickListener {
            proximo()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }

        binding.icBack.setOnClickListener {
            voltar()
        }
    }

    fun isValidProfileData(
        cpf: String,
        data: String
    ): Boolean {
        return when {
            cpf.isEmpty() ||
                data.isEmpty() -> false
            else -> true
        }
    }

    fun proximo() {

        if (isValidProfileData(
                binding.cpfInput.editText?.text.toString(),
                binding.dataNascInput.editText?.text.toString()
            )
        ) {
            val freelancer = Intent(this, Etapa3Activity::class.java)
            startActivity(freelancer)
            finish()
        } else {
            Toast.makeText(this, "Preencha todos os dados corretamente", Toast.LENGTH_SHORT).show()
        }
    }

    fun voltar() {
        val voltar = Intent(this, Etapa1Activity::class.java)
        startActivity(voltar)
        finish()
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

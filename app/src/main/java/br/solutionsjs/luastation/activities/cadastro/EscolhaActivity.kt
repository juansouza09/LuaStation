package br.solutionsjs.luastation.activities.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.CadastroEscolhaScreenBinding

class EscolhaActivity : AppCompatActivity() {

    private lateinit var binding: CadastroEscolhaScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroEscolhaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEmpresa.let {
            it.setOnClickListener {
                empresa()
            }
        }

        binding.buttonFreelancer.let {
            it.setOnClickListener {
                freelancer()
            }
        }

        binding.buttonCancelar.let {
            it.setOnClickListener {
                cancelar()
            }
        }
    }

    private fun empresa() {
        startActivity(Intent(this, EtapaEmpresaActivity::class.java))
        finish()
    }

    private fun freelancer() {
        startActivity(Intent(this, Etapa3Activity::class.java))
        finish()
    }

    private fun cancelar() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

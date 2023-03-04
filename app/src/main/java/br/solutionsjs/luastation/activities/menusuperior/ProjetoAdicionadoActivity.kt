package br.solutionsjs.luastation.activities.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.activities.HomeActivity
import br.solutionsjs.luastation.databinding.ProjetoAdicionadoScreenBinding

class ProjetoAdicionadoActivity : AppCompatActivity() {

    private val binding by lazy { ProjetoAdicionadoScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnVoltar.let {
            it.setOnClickListener {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}

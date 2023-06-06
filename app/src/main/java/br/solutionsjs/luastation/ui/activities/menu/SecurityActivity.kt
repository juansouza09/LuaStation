package br.solutionsjs.luastation.ui.activities.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.databinding.ActivitySecurityBinding

class SecurityActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySecurityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.icBack.let {
            it.setOnClickListener {
                voltar()
            }
        }
    }

    private fun voltar() {
        val voltar = Intent(this, MenuActivity::class.java)
        startActivity(voltar)
        finish()
    }
}

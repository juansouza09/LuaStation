package br.solutionsjs.luastation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.ErrorSignupActivityBinding

class ErrorSignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ErrorSignupActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.button.let {
            it.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}

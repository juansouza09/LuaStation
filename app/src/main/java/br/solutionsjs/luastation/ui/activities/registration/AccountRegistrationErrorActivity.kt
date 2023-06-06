package br.solutionsjs.luastation.ui.activities.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.ui.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.ActivityAccountRegistrationErrorBinding

class AccountRegistrationErrorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAccountRegistrationErrorBinding.inflate(layoutInflater) }

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

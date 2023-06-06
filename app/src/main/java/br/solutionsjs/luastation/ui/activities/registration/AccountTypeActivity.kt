package br.solutionsjs.luastation.ui.activities.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.ui.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.ActivityAccountTypeBinding

class AccountTypeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAccountTypeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        startActivity(Intent(this, CompanyRegistrationActivity::class.java))
        finish()
    }

    private fun freelancer() {
        startActivity(Intent(this, AccountValidationActivity::class.java))
        finish()
    }

    private fun cancelar() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

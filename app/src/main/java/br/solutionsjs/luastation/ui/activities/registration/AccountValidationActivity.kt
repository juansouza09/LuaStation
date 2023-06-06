package br.solutionsjs.luastation.ui.activities.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.ui.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.ActivityAccountValidationBinding

class AccountValidationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAccountValidationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonFinalizar.setOnClickListener {
            validate()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }

        binding.icBack.setOnClickListener {
            voltar()
        }
    }

    private fun validate() {
        if (!binding.checkBaixaRenda.isChecked && !binding.checkLgbt.isChecked && !binding.checkMulher.isChecked && !binding.checkPreto.isChecked) {
            val intent = Intent(this, AccountRegistrationErrorActivity::class.java)
            startActivity(intent)
        } else {
            proximo()
        }
    }

    private fun proximo() {
        val freelancer = Intent(this, FreelancerRegistrationActivity::class.java)
        startActivity(freelancer)
        finish()
    }

    private fun voltar() {
        val voltar = Intent(this, AccountTypeActivity::class.java)
        startActivity(voltar)
        finish()
    }

    private fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

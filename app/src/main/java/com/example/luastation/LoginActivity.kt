package com.example.luastation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.luastation.cadastro.EscolhaActivity
import com.example.luastation.databinding.LoginScreenBinding
import com.example.luastation.db.AppDatabase
import com.example.luastation.db.login.LoginViewModel
import com.example.luastation.db.repository.UserDbDataSource
import com.example.luastation.db.repository.UserRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginScreenBinding
    private val viewModel : LoginViewModel by viewModels {
        val dataBase = AppDatabase.getDatabase(this)
        val userRepository: UserRepository = UserDbDataSource(dataBase.userDao())
        LoginViewModel.LoginViewModelFactory(userRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            abrirHome()
        }

        binding.buttonCadastrar.setOnClickListener {
            abrirCadastro()
        }
    }

    fun abrirHome(){
        lifecycleScope.launchWhenStarted {
            val sucesso = viewModel.login(
                binding.emailInput.editText?.text.toString(),
                binding.passwordInput.editText?.text.toString()
            )
            if (sucesso) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
            else {
                Toast.makeText(this@LoginActivity, "Falha no login! Os dados estão incorretos!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun abrirCadastro(){
        val cadastro = Intent(this, EscolhaActivity::class.java)
        startActivity(cadastro)
        finish()
    }
}
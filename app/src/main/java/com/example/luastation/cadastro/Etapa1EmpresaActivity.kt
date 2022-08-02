package com.example.luastation.cadastro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.CadastroScreenBinding
import com.example.luastation.db.AppDatabase
import com.example.luastation.db.registration.RegistrationViewModel
import com.example.luastation.db.repository.UserDbDataSource
import com.example.luastation.db.repository.UserRepository

class Etapa1EmpresaActivity : AppCompatActivity() {
    private lateinit var binding: CadastroScreenBinding
    private val viewModel: RegistrationViewModel by viewModels {
        val dataBase = AppDatabase.getDatabase(this)
        val userRepository: UserRepository = UserDbDataSource(dataBase.userDao())
        RegistrationViewModel.RegistrationViewModelFactory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonProximo.setOnClickListener {
            if (viewModel.isValidProfileData(
                    binding.nomeSocialInput.editText?.text.toString(),
                    binding.nomeInput.editText?.text.toString(),
                    binding.emailInput.editText?.text.toString(),
                    binding.passwordInput.editText?.text.toString()

                )
            ) {
                viewModel.createUser(
                    binding.nomeSocialInput.editText?.text.toString(),
                    binding.nomeInput.editText?.text.toString(),
                    binding.emailInput.editText?.text.toString(),
                    binding.passwordInput.editText?.text.toString()
                )
                val freelancer = Intent(this, Etapa2EmpresaActivity::class.java)
                startActivity(freelancer)
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os dados corretamente", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

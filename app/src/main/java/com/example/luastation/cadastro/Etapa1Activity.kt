package com.example.luastation.cadastro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.LoginActivity
import com.example.luastation.databinding.CadastroScreenBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Etapa1Activity : AppCompatActivity() {

    private lateinit var binding: CadastroScreenBinding
    private lateinit var dbRef: DatabaseReference
    private val viewModel: CadastroViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        binding.buttonProximo.setOnClickListener {

            if (viewModel.isValidProfileData(
                    binding.nomeInput.editText?.text.toString(),
                    binding.emailInput.editText?.text.toString(),
                    binding.passwordInput.editText?.text.toString(),
                    binding.dataNascInput.editText?.text.toString(),
                    binding.cpfInput.editText?.text.toString()
                )
            ) {
                saveUser(
                    binding.nomeInput.editText?.text.toString(),
                    binding.emailInput.editText?.text.toString(),
                    binding.passwordInput.editText?.text.toString(),
                    binding.dataNascInput.editText?.text.toString(),
                    binding.cpfInput.editText?.text.toString()
                )
                val freelancer = Intent(this, HomeActivity::class.java)
                startActivity(freelancer)
                finish()
            } else {
                Toast.makeText(this, "Dados incorretos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }


    private fun saveUser(
        name: String,
        email: String,
        password: String,
        dataNasc: String,
        cpf: String
    ) {

        val UserId = dbRef.push().key!!

        val User = UserModel(UserId, name, email, password, dataNasc, cpf)

        dbRef.child(UserId).setValue(User)
            .addOnCompleteListener {
                Toast.makeText(this, "Usuário criado", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Usuário não foi criado ${err.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }


    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }
}

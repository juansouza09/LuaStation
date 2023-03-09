package com.solutions.luastation.activities.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.solutions.luastation.activities.HomeActivity
import com.solutions.luastation.activities.cadastro.EscolhaActivity
import com.solutionsjs.luastation.databinding.LoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.hiperLink.movementMethod = LinkMovementMethod.getInstance()

        binding.buttonEntrar.setOnClickListener {
            lifecycle.coroutineScope.launch { validateData() }
        }

        binding.buttonCadastrar.setOnClickListener {
            abrirCadastro()
        }
    }

    private suspend fun validateData() {
        email = binding.emailInput.editText?.text.toString().trim()
        password = binding.passwordInput.editText?.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "E-mail inválido!"
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordInput.error = "Por favor, insira a senha!"
        } else {
            firebaseLogin()
        }
    }

    private suspend fun firebaseLogin() = coroutineScope {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this@LoginActivity, "Entrou como $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@LoginActivity,
                    "Login não efetuado, tente novamente ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun abrirCadastro() {
        val cadastro = Intent(this, EscolhaActivity::class.java)
        startActivity(cadastro)
        finish()
    }
}

package com.example.luastation.firebase.cadastro

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.databinding.CadastroEmpresaScreenBinding
import com.example.luastation.firebase.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Etapa1EmpresaActivity : AppCompatActivity() {
    private lateinit var binding: CadastroEmpresaScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var email = ""
    private var password = ""
    private var name = ""
    private var cnpj = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroEmpresaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cnpj = binding.cnpjInput.editText
        cnpj?.addTextChangedListener(MaskEditUtil.mask(cnpj))
        database = FirebaseDatabase.getInstance().getReference("Users").child("Empresas")
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonProximo.setOnClickListener {

            validateData()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    private fun validateData() {
        email = binding.emailInput.editText?.text.toString().trim()
        password = binding.passwordInput.editText?.text.toString().trim()
        name = binding.nomeInput.editText?.text.toString().trim()
        cnpj = binding.cnpjInput.editText?.text.toString().trim()

        if (TextUtils.isEmpty(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
        } else if (TextUtils.isEmpty(cnpj)) {
            binding.cnpjInput.error = "Por favor, insira o Cnpj!"
        } else if (cnpj.length < 14) {
            binding.cnpjInput.error = "Por favor, insira o Cnpj corretamente!"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "E-mail inválido!"
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordInput.error = "Por favor, insira a senha!"
        } else if (password.length < 5) {
            binding.passwordInput.error = "A senha deve ter no mínimo 6 carácteres!"
        } else {
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                val currentUserDb = database.child((firebaseUser.uid))
                currentUserDb.child("name").setValue(binding.nomeInput.editText?.text.toString())
                currentUserDb.child("cnpj").setValue(binding.cnpjInput.editText?.text.toString())

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Cadastro não finalizado ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }

    object MaskEditUtil {
        fun mask(ediTxt: EditText): TextWatcher {
            var isUpdating: Boolean = false
            var mask = ""
            var old = ""
            return object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val str = unmask(s.toString())
                    var mascara = ""
                    when (str.length) {
                        in 0..11 -> mask = "##.###.###/####-##"
                        else -> mask = "##.###.###/####-##"
                    }
                    if (isUpdating) {
                        old = str
                        isUpdating = false
                        return
                    }
                    var i = 0
                    for (m in mask.toCharArray()) {
                        if (m != '#' && str.length > old.length) {
                            mascara += m
                            continue
                        }
                        try {
                            mascara += str[i]
                        } catch (e: Exception) {
                            break
                        }
                        i++
                    }
                    isUpdating = true
                    ediTxt.setText(mascara)
                    ediTxt.setSelection(mascara.length)
                }
            }
        }

        fun unmask(s: String): String {
            return s.replace("-", "").replace("/", "").replace(".", "")
        }
    }
}

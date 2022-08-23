package com.example.luastation.firebase.cadastro

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.databinding.CadastroScreenBinding
import com.example.luastation.firebase.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Etapa1Activity : AppCompatActivity() {

    private lateinit var binding: CadastroScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var email = ""
    private var password = ""
    private var cpf_cnpj = ""
    private var name = ""
    private var dataNasc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cpf = binding.cpfCnpjInput.editText
        cpf?.addTextChangedListener(MaskEditUtil.mask(cpf))

        val date = binding.dataNascInput.editText
        DateInputMask(date).listen()

        database = FirebaseDatabase.getInstance().getReference("Users")
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonProximo.setOnClickListener {
            validateData()
            it.isClickable = false
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    private fun validateData() {
        email = binding.emailInput.editText?.text.toString().trim()
        password = binding.passwordInput.editText?.text.toString().trim()
        cpf_cnpj = binding.cpfCnpjInput.editText?.text.toString().trim()
        name = binding.nomeInput.editText?.text.toString().trim()
        dataNasc = binding.dataNascInput.editText?.text.toString().trim()

        if (TextUtils.isEmpty(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
        } else if (TextUtils.isEmpty(cpf_cnpj)) {
            binding.cpfCnpjInput.error = "Por favor, insira o dado!"
        } else if (cpf_cnpj.length < 11) {
            binding.cpfCnpjInput.error = "Por favor, insira o dado corretamente!"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "E-mail inválido!"
        } else if (TextUtils.isEmpty(dataNasc)) {
            binding.dataNascInput.error = "Por favor, insira a Data de Nascimento!"
        } else if (dataNasc.length != 10) {
            binding.dataNascInput.error = "Por favor, insira a Data de Nascimento corretamente!"
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

                binding.progressBar.visibility = View.VISIBLE

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                val currentUserDb = database.child((firebaseUser.uid))
                currentUserDb.child("id").setValue((firebaseUser.uid))
                currentUserDb.child("name").setValue(binding.nomeInput.editText?.text.toString())
                currentUserDb.child("dataNasc")
                    .setValue(binding.dataNascInput.editText?.text.toString())
                currentUserDb.child("email").setValue(binding.emailInput.editText?.text.toString())
                currentUserDb.child("cpf_cnpj")
                    .setValue(binding.cpfCnpjInput.editText?.text.toString())

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
                        in 0..11 -> mask = "###.###.###-##"
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

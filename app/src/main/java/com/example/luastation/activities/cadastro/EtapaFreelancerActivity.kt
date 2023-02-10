package com.example.luastation.activities.cadastro

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
import androidx.core.view.isVisible
import com.example.luastation.activities.HomeActivity
import com.example.luastation.activities.login.LoginActivity
import com.example.luastation.databinding.CadastroScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EtapaFreelancerActivity : AppCompatActivity() {

    private lateinit var binding: CadastroScreenBinding
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }

    private var email = ""
    private var password = ""
    private var cpf = ""
    private var name = ""
    private var dataNasc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMasks()

        binding.buttonProximo.setOnClickListener {
            validateData()
            it.isClickable = false
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    private fun setMasks() {
        binding.cpfInput.isVisible = true
        val cpf = binding.cpfInput.editText
        cpf?.addTextChangedListener(MaskEditUtil.mask(cpf))

        val date = binding.dataNascInput.editText
        DateInputMask(date).listen()
    }

    private fun validateData() {
        email = binding.emailInput.editText?.text.toString().trim()
        password = binding.passwordInput.editText?.text.toString().trim()
        cpf = binding.cpfInput.editText?.text.toString().trim()
        name = binding.nomeInput.editText?.text.toString().trim()
        dataNasc = binding.dataNascInput.editText?.text.toString().trim()

        val nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex()

        if (TextUtils.isEmpty(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
        } else if (!nameRegex.matches(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
        } else if (TextUtils.isEmpty(cpf)) {
            binding.cpfInput.error = "Por favor, insira o dado!"
        } else if (cpf.length < 11) {
            binding.cpfInput.error = "Por favor, insira o dado corretamente!"
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
                val firebaseUser = firebaseAuth.currentUser!!
                val currentUserDb = database.child((firebaseUser.uid))

                currentUserDb.child("id").setValue((firebaseUser.uid))
                currentUserDb.child("name").setValue(binding.nomeInput.editText?.text.toString())
                currentUserDb.child("dataNasc")
                    .setValue(binding.dataNascInput.editText?.text.toString())
                currentUserDb.child("email").setValue(binding.emailInput.editText?.text.toString())
                currentUserDb.child("cpf_cnpj")
                    .setValue(binding.cpfInput.editText?.text.toString())

                binding.progressBar.visibility = View.VISIBLE
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Cadastro não finalizado ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun cancelar() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }

    object MaskEditUtil {
        fun mask(ediTxt: EditText): TextWatcher {
            var isUpdating = false
            var mask: String
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
                    mask = when (str.length) {
                        in 0..11 -> "###.###.###-##"
                        else -> "###.###.###-##"
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

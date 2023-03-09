package com.solutions.luastation.activities.cadastro

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
import com.solutions.luastation.activities.HomeActivity
import com.solutions.luastation.activities.login.LoginActivity
import com.solutionsjs.luastation.databinding.CadastroScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EtapaEmpresaActivity : AppCompatActivity() {

    private lateinit var binding: CadastroScreenBinding
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }

    private var email = ""
    private var password = ""
    private var cnpj = ""
    private var name = ""
    private var dataNasc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CadastroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMasks()

        binding.buttonProximo.setOnClickListener {
            if (validateData()) firebaseSignUp()
        }

        binding.btnCancelar.setOnClickListener {
            cancelar()
        }
    }

    private fun setMasks() {
        binding.cnpjInput.isVisible = true
        val cnpj = binding.cnpjInput.editText
        cnpj?.addTextChangedListener(MaskEditUtil.mask(cnpj))

        val date = binding.dataNascInput.editText
        DateInputMask(date).listen()
    }

    private fun validateData(): Boolean {
        email = binding.emailInput.editText?.text.toString().trim()
        password = binding.passwordInput.editText?.text.toString().trim()
        cnpj = binding.cnpjInput.editText?.text.toString().trim()
        name = binding.nomeInput.editText?.text.toString().trim()
        dataNasc = binding.dataNascInput.editText?.text.toString().trim()
        var year: Int? = null
        var mouth: Int? = null
        if (binding.dataNascInput.editText?.text.toString().isNotEmpty()) {
            year = binding.dataNascInput.editText?.text.toString().substring(6).toInt()
        }
        if (binding.dataNascInput.editText?.text.toString().isNotEmpty()) {
            mouth = binding.dataNascInput.editText?.text.toString().substring(3, 5).toInt()
        }

        val nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex()

        if (TextUtils.isEmpty(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
            return false
        } else if (!nameRegex.matches(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
            return false
        } else if (TextUtils.isEmpty(cnpj)) {
            binding.cpfInput.error = "Por favor, insira o dado!"
            return false
        } else if (cnpj.length < 18) {
            binding.cnpjInput.error = "Por favor, insira o dado corretamente!"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "E-mail inválido!"
            return false
        } else if (TextUtils.isEmpty(dataNasc)) {
            binding.dataNascInput.error = "Por favor, insira a Data de Nascimento!"
            return false
        } else if (dataNasc.length != 10) {
            binding.dataNascInput.error = "Por favor, insira a Data de Nascimento corretamente!"
            return false
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordInput.error = "Por favor, insira a senha!"
            return false
        } else if (password.length < 5) {
            binding.passwordInput.error = "A senha deve ter no mínimo 6 carácteres!"
            return false
        } else if (year != null) {
            if (year >= 2008) {
                binding.dataNascInput.error = "A idade miníma é 16 anos!"
                return false
            } else if (year < 1935) {
                binding.dataNascInput.error = "Por favor, insira a Data de Nascimento corretamente!"
                return false
            } else if (mouth != null) {
                return if (mouth > 12 || mouth < 1) {
                    binding.dataNascInput.error =
                        "Por favor, insira a Data de Nascimento corretamente!"
                    false
                } else true
            }
        }
        return true
    }

    private fun firebaseSignUp() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser!!
                val currentUserDb = database.child((firebaseUser.uid))

                currentUserDb.child("id").setValue((firebaseUser.uid))
                currentUserDb.child("name").setValue(name)
                currentUserDb.child("dataNasc")
                    .setValue(dataNasc)
                currentUserDb.child("email").setValue(email)
                currentUserDb.child("cpf_cnpj")
                    .setValue(cnpj)

                binding.progressBar.visibility = View.VISIBLE
                startActivity(Intent(this@EtapaEmpresaActivity, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@EtapaEmpresaActivity,
                    "Cadastro não finalizado ${e.message}",
                    Toast.LENGTH_SHORT
                )
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
                        in 0..14 -> "##.###.###/####-##"
                        else -> "##.###.###/####-##"
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

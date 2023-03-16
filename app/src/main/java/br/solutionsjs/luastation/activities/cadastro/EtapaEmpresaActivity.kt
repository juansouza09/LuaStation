package br.solutionsjs.luastation.activities.cadastro

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
import br.solutionsjs.luastation.activities.HomeActivity
import br.solutionsjs.luastation.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.CadastroScreenBinding
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

        binding.cnpjInput.error = null
        binding.emailInput.error = null
        binding.passwordInput.error = null
        binding.nomeInput.error = null
        binding.dataNascInput.error = null

        val year = dataNasc.takeIf { it.length == 10 }?.substring(6)?.toInt()
        val month = dataNasc.takeIf { it.length == 10 }?.substring(3, 5)?.toInt()

        fun isValidName(name: String): Boolean {
            val nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex()
            return !TextUtils.isEmpty(name) && nameRegex.matches(name)
        }

        if (!isValidName(name)) {
            binding.nomeInput.error = "Por favor, insira o Nome!"
            return false
        }

        when {
            TextUtils.isEmpty(cnpj) -> {
                binding.cnpjInput.error = "Por favor, insira o dado!"
                return false
            }
            cnpj.length < 18 -> {
                binding.cnpjInput.error = "Por favor, insira o dado corretamente!"
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInput.error = "E-mail inválido!"
                return false
            }
            TextUtils.isEmpty(dataNasc) -> {
                binding.dataNascInput.error = "Por favor, insira a Data de Nascimento!"
                return false
            }
            dataNasc.length != 10 -> {
                binding.dataNascInput.error = "Por favor, insira a Data de Nascimento corretamente!"
                return false
            }
            TextUtils.isEmpty(password) -> {
                binding.passwordInput.error = "Por favor, insira a senha!"
                return false
            }
            password.length < 5 -> {
                binding.passwordInput.error = "A senha deve ter no mínimo 6 carácteres!"
                return false
            }
            year != null && year >= 2008 -> {
                binding.dataNascInput.error = "A idade miníma é 16 anos!"
                return false
            }
            year != null && year < 1935 -> {
                binding.dataNascInput.error = "Por favor, insira a Data de Nascimento corretamente!"
                return false
            }
            month != null && (month > 12 || month < 1) -> {
                binding.dataNascInput.error = "Por favor, insira a Data de Nascimento corretamente!"
                return false
            }
            else -> return true
        }
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

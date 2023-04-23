package br.solutionsjs.luastation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.databinding.ActivityApplyProjectBinding
import br.solutionsjs.luastation.models.Notification
import com.google.firebase.database.FirebaseDatabase

class ApplyProjectActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityApplyProjectBinding.inflate(layoutInflater)
    }

    private val dbReference by lazy {
        FirebaseDatabase.getInstance().getReference("Notification")
    }

    private var serviceId: String? = null
    private var creatorId: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dadosIntent()
        setupListeners()
    }

    private fun dadosIntent() {
        val intent = intent
        val aCreator = intent.getStringExtra("eCreator")
        val aIdService = intent.getStringExtra("eId")
        val aTitle = intent.getStringExtra("eTitle")

        serviceId = aIdService
        creatorId = aCreator
        title = aTitle

        binding.h1.text = aTitle
    }

    private fun setupListeners() {
        binding.icBack.let {
            it.setOnClickListener {
                val intent = Intent(this@ApplyProjectActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.buttonProximo.let {
            it.setOnClickListener {
                if (validateInputs()) {
                    saveInvite()
                    val intent = Intent(this@ApplyProjectActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val desc = binding.descInput.editText?.text.toString()
        val email = binding.emailInviteInput.editText?.text.toString()
        return if (email.isEmpty() && desc.isEmpty()
        ) {
            binding.emailInviteInput.error = "Adicione seu e-mail"
            binding.descInput.error = "Adicione uma descrição"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInviteInput.error = "Adicione um e-mail válido"
            return false
        } else if (email.isEmpty()) {
            binding.emailInviteInput.error = "Adicione seu e-mail"
            false
        } else if (desc.isEmpty()) {
            binding.descInput.error = "Adicione uma descrição"
            false
        } else {
            true
        }
    }

    private fun saveInvite() {
        val email = binding.emailInviteInput.editText
        val desc = binding.descInput.editText
        val notificationId = dbReference.push().key!!
        val notification = Notification(
            notificationId,
            title,
            email?.text.toString(),
            desc?.text.toString()
        )

        creatorId?.let { it ->
            dbReference
                .child(it)
                .child(notificationId)
                .setValue(notification)
                .let {
                    it.addOnCompleteListener {
                        binding.buttonProximo.isClickable = false
                        startActivity(Intent(this@ApplyProjectActivity, HomeActivity::class.java))
                        finish()
                    }
                    it.addOnFailureListener {
                        Toast.makeText(
                            this@ApplyProjectActivity,
                            "O projeto $title não foi armazenado!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
        }
    }
}

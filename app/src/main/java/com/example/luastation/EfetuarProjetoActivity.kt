package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding
import com.example.luastation.databinding.EfetuarProjetoScreenBinding
import com.example.luastation.firebase.models.Notification
import com.example.luastation.firebase.models.Services
import com.example.luastation.menusuperior.PagamentoProjetoActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EfetuarProjetoActivity : AppCompatActivity() {

    private lateinit var binding: EfetuarProjetoScreenBinding
    private lateinit var dbReference: DatabaseReference

    private var serviceId: String? = null
    private var creatorId: String? = null
    private var titleService: String? = null
    private lateinit var email: EditText
    private lateinit var desc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EfetuarProjetoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        saveInvite()
        email = binding.emailInviteInput.editText!!
        desc = binding.descInput.editText!!

        dadosIntent()
        listeners()
    }

    fun dadosIntent() {
        var intent = intent
        val aIdService = intent.getStringExtra("iId")
        val aCreator = intent.getStringExtra("iCreator")
        val aTitle = intent.getStringExtra("iTitle")

        serviceId = aIdService
        creatorId = aCreator
        titleService = aTitle


    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveInvite() {
        dbReference = FirebaseDatabase.getInstance().getReference("Notification")
        val notificationId = dbReference.push().key!!
        val notification = Notification(
            notificationId,
            title = titleService,
            email = email.text.toString(),
            description = desc.text.toString()
        )

        dbReference.child(creatorId!!).child(notificationId).setValue(notification)
            .addOnCompleteListener {
                binding.buttonProximo.isClickable = false
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "O projeto $title não foi armazenado!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
    }
}

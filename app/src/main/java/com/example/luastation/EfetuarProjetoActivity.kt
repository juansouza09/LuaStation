package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.EfetuarProjetoScreenBinding
import com.example.luastation.firebase.models.Notification
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EfetuarProjetoActivity : AppCompatActivity() {

    private lateinit var binding: EfetuarProjetoScreenBinding
    private lateinit var dbReference: DatabaseReference

    private var serviceId: String? = null
    private var creatorId: String? = null
    private var title: String? = null
    private var email: EditText? = null
    private var desc: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EfetuarProjetoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        email = binding.emailInviteInput.editText
        desc = binding.descInput.editText

        dadosIntent()
        listeners()
    }

    fun dadosIntent() {
        val intent = intent
        val aCreator = intent.getStringExtra("eCreator")
        val aIdService = intent.getStringExtra("eId")
        val aTitle = intent.getStringExtra("eTitle")

        serviceId = aIdService
        creatorId = aCreator
        title = aTitle

        binding.h1.text = aTitle
    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonProximo.setOnClickListener {
            saveInvite()
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
            title,
            email?.text.toString(),
            desc?.text.toString()
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

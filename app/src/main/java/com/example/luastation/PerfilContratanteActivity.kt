package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding
import com.google.firebase.database.*

class PerfilContratanteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilUsuarioBinding
    private lateinit var database: DatabaseReference
    private var creatorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dadosIntent()
        getContratanteData()
        listeners()
    }

    private fun dadosIntent() {
        val intent = intent
        val aCreatorId = intent.getStringExtra("eCreatorId")

        if (aCreatorId != null) {
            creatorId = aCreatorId
        }
    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getContratanteData() {
        database = FirebaseDatabase.getInstance().getReference("Users").child(creatorId!!)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.textNameContratante.text = snapshot.child("name").value.toString()
                binding.perfilNameText.text = snapshot.child("name").value.toString()
                binding.perfilCpfCnpjText.text = snapshot.child("cpf_cnpj").value.toString()
                binding.perfilEmailText.text = snapshot.child("email").value.toString()
                binding.perfilNascText.text = snapshot.child("dataNasc").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

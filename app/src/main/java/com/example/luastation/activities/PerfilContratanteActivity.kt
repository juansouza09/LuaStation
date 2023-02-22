package com.example.luastation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding
import com.example.luastation.models.Services
import com.google.firebase.database.*

class PerfilContratanteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users").child(creatorId!!)
    }
    private var creatorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dadosIntent()
        getContratanteData()
        setupListeners()
    }

    private fun dadosIntent() {
        val intent = intent
        val aCreatorId = intent.getStringExtra("eCreatorId")

        if (aCreatorId != null) {
            creatorId = aCreatorId
        }
    }

    private fun setupListeners() {
        binding.icBack.let {
            it.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getContratanteData() {
        database.apply {
            addValueEventListener(object : ValueEventListener {
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
        database.child("Meus Projetos").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelasList = mutableListOf<Services>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val freela = serviceSnapshot.getValue(Services::class.java)
                        freelasList.add(freela!!)
                    }
                    setUi(freelasList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setUi(services: List<Services>) {
        with(binding) {
            val serviceMoonCount = services.filter { it.type == "lua" }.size
            val serviceStarCount = services.filter { it.type == "estrela" }.size
            val serviceMeteoroCount = services.filter { it.type == "meteoro" }.size

            itemCountLua.text = serviceMoonCount.toString()
            itemCountEstrela.text = serviceStarCount.toString()
            itemCountMeteoro.text = serviceMeteoroCount.toString()
        }
    }
}

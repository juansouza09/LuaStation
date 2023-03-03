package com.example.luastation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding
import com.example.luastation.models.Services
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PerfilFreelancerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }

    private val database by lazy {
        freelancerId?.let { FirebaseDatabase.getInstance().getReference("Users").child(it) }
    }

    private var freelancerId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dadosIntent()
        getContratanteData()
        setupListeners()
    }

    private fun dadosIntent() {
        val intent = intent
        val name = intent.getStringExtra("iName")
        val email = intent.getStringExtra("iEmail")
        val dataNasc = intent.getStringExtra("iDataNasc")
        val cpf_cnpj = intent.getStringExtra("iCpf_cnpj")
        val id = intent.getStringExtra("iFreelancerID")

        freelancerId = id

        binding.textNameContratante.text = name
        binding.perfilNameText.text = name
        binding.perfilCpfCnpjText.text = cpf_cnpj
        binding.perfilEmailText.text = email
        binding.perfilNascText.text = dataNasc
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
        database?.child("Meus Projetos")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelasList = mutableListOf<Services?>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val freela = serviceSnapshot.getValue(Services::class.java)
                        freelasList.add(freela)
                    }
                    setUi(freelasList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setUi(services: List<Services?>) {
        binding.run {
            val serviceMoonCount = services.filter { it?.type == "lua" }.size
            val serviceStarCount = services.filter { it?.type == "estrela" }.size
            val serviceMeteoroCount = services.filter { it?.type == "meteoro" }.size
            val userLevel = serviceMeteoroCount + serviceMoonCount + serviceStarCount

            textLevel.text = userLevel.toString()
            itemCountLua.text = serviceMoonCount.toString()
            itemCountEstrela.text = serviceStarCount.toString()
            itemCountMeteoro.text = serviceMeteoroCount.toString()
        }
    }
}

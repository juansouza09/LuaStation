package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityServicoDetalhesBinding
import com.example.luastation.firebase.models.Services
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DetalhesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicoDetalhesBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicoDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        detalhesIntent()
        getServiceData()
    }



    private fun detalhesIntent() {
        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aPrice = intent.getStringExtra("iPrice")
        val aDays = intent.getStringExtra("iDays")
        val aPlataform = intent.getStringExtra("iPlataform")
        val aDesc = intent.getStringExtra("iDesc")
        val aImg = intent.getStringExtra("iImg")
        val aCreator = intent.getStringExtra("iCreator")

        binding.titleTextFreela.text = aTitle
        binding.priceTextFreela.text = aPrice
        binding.timeText.text = aDays
        binding.plataformaText.text = aPlataform
        binding.textDesc.text = aDesc
        binding.textNameContratante.text = aCreator

        Picasso.get().load(aImg).into(binding.imgDificultade)
    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imgContratante.setOnClickListener {
            val intent = Intent(this, PerfilContratanteActivity::class.java)
            startActivity(intent)
        }

        binding.btnCanditadar.setOnClickListener {
            Toast.makeText(this, "Boa sorte, Astronauta!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EfetuarProjetoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getServiceData() {
        val aCreator = intent.getStringExtra("iCreator")
        database = FirebaseDatabase.getInstance().getReference("Users").child(aCreator.toString())
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.textNameContratante.text = snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

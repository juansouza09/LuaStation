package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityServicoDetalhesBinding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DetalhesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityServicoDetalhesBinding.inflate(layoutInflater)
    }
    private lateinit var database: DatabaseReference
    private var creator: String? = null
    private var serviceId: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()
        detalhesIntent()
        getContratanteData()
    }

    private fun detalhesIntent() {
        val intent = intent
        val aServiceId = intent.getStringExtra("iId")
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

        if (aCreator != null && aTitle != null && aServiceId != null) {
            creator = aCreator
            title = aTitle
            serviceId = aServiceId
        }
    }

    private fun listeners() {
        binding.icBack.apply {
            setOnClickListener {
                val intent = Intent(this@DetalhesActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.imgContratante.apply {
            setOnClickListener {
                val intent = Intent(this@DetalhesActivity, PerfilContratanteActivity::class.java)
                intent.putExtra("eCreatorId", creator)
                startActivity(intent)
            }
        }

        binding.btnCanditadar.apply {
            setOnClickListener {
                Toast.makeText(this@DetalhesActivity, "Boa sorte, Astronauta!", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@DetalhesActivity, EfetuarProjetoActivity::class.java)
                intent.putExtra("eCreator", creator)
                intent.putExtra("eId", serviceId)
                intent.putExtra("eTitle", title)
                startActivity(intent)
            }
        }
    }

    private fun getContratanteData() {
        database = FirebaseDatabase.getInstance().getReference("Users").child(creator!!)
        database.apply {
            addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.textNameContratante.text = snapshot.child("name").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}

package br.solutionsjs.luastation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.databinding.ActivityServiceDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ServiceDetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityServiceDetailsBinding.inflate(layoutInflater) }
    private val database by lazy {
        creator?.let { FirebaseDatabase.getInstance().getReference("Users").child(it) }
    }
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private var creator: String? = null
    private var serviceId: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
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

        if (aCreator == firebaseAuth.currentUser?.uid) {
            binding.btnCanditadar.setOnClickListener {
                Toast.makeText(this, "Você é o dono desse projeto", Toast.LENGTH_SHORT).show()
            }
        }

        if (aCreator != firebaseAuth.currentUser?.uid) {
            binding.btnCanditadar.let {
                it.setOnClickListener {
                    Toast.makeText(this, "Boa sorte, Astronauta!", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("detalhes", "clicou e n é dono")
                    val theIntent = Intent(this, ApplyProjectActivity::class.java)
                    theIntent.putExtra("eCreator", creator)
                    theIntent.putExtra("eId", serviceId)
                    theIntent.putExtra("eTitle", title)
                    startActivity(theIntent)
                }
            }
        }

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

    private fun setupListeners() {
        binding.icBack.let {
            it.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.imgContratante.let {
            it.setOnClickListener {
                val intent = Intent(this, CompanyProfileActivity::class.java)
                intent.putExtra("eCreatorId", creator)
                startActivity(intent)
            }
        }
    }

    private fun getContratanteData() {
        database?.apply {
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

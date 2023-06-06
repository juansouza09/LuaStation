package br.solutionsjs.luastation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.databinding.ActivityUserProfileBinding
import br.solutionsjs.luastation.data.models.Services
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FreelancerProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityUserProfileBinding.inflate(layoutInflater)
    }

    private val database by lazy {
        freelancerId?.let { FirebaseDatabase.getInstance().getReference("Users").child(it) }
    }

    private var freelancerId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataIntent()
        getFreelancerData()
        setupListeners()
    }

    private fun dataIntent() {
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

    private fun getFreelancerData() {
        database?.child("Meus Projetos")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val servicesList = mutableListOf<Services?>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = serviceSnapshot.getValue(Services::class.java)
                        servicesList.add(service)
                    }
                    setUi(servicesList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setUi(services: List<Services?>) {
        with(binding) {
            val moonServiceCount = services.filter { it?.type == "lua" }.size
            val starServiceCount = services.filter { it?.type == "estrela" }.size
            val meteorServiceCount = services.filter { it?.type == "meteoro" }.size
            val userLevel = meteorServiceCount + moonServiceCount + starServiceCount

            textLevel.text = userLevel.toString()
            itemCountLua.text = moonServiceCount.toString()
            itemCountEstrela.text = starServiceCount.toString()
            itemCountMeteoro.text = meteorServiceCount.toString()
        }
    }
}

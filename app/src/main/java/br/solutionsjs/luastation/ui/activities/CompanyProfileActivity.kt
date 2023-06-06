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

class CompanyProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityUserProfileBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        creatorId?.let { FirebaseDatabase.getInstance().getReference("Users").child(it) }
    }
    private var creatorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataIntent()
        getCompanyData()
        setupListeners()
    }

    private fun dataIntent() {
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

    private fun getCompanyData() {
        database?.apply {
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
            val startServiceCount = services.filter { it?.type == "estrela" }.size
            val meteorServiceCount = services.filter { it?.type == "meteoro" }.size
            val userLevel = meteorServiceCount + moonServiceCount + startServiceCount

            textLevel.text = userLevel.toString()
            itemCountLua.text = moonServiceCount.toString()
            itemCountEstrela.text = startServiceCount.toString()
            itemCountMeteoro.text = meteorServiceCount.toString()
        }
    }
}

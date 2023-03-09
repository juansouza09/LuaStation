package com.solutions.luastation.activities.menusuperior

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.solutionsjs.luastation.databinding.CriarProjeto2ScreenBinding
import com.solutions.luastation.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CriarProjeto2Activity : AppCompatActivity() {

    private val binding by lazy { CriarProjeto2ScreenBinding.inflate(layoutInflater) }

    private val dbRef by lazy {
        FirebaseDatabase.getInstance().getReference("Services")
    }
    private val dbRef2 by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseAuth.currentUser!!.uid).child("Meus Projetos")
    }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.icBack.setOnClickListener {
            startActivity(Intent(this, CriarProjetoActivity::class.java))
            finish()
        }

        binding.buttonProximo.setOnClickListener {
            saveServiceData()
        }
    }

    private fun saveServiceData() {
        val name = binding.nomeProjetoInput.editText?.text
        val price = binding.priceInput.editText?.text
        val days = binding.prazoInput.editText?.text
        val desc = binding.descInput.editText?.text
        val plataform = binding.plataformaInput.editText?.text

        val creatorId = firebaseAuth.currentUser?.uid
        val serviceName = name.toString()
        val servicePrice = "R$ " + price.toString()
        val serviceDays = days.toString() + " dias"
        val serviceDesc = desc.toString()
        val servicePlataform = plataform.toString()

        if (serviceName.isEmpty()) {
            binding.nomeProjetoInput.editText?.error = "Coloque o nome corretamente, por favor!"
        } else if (price?.isEmpty() == true) {
            binding.priceInput.editText?.error = "Coloque o preço corretamente, por favor!"
        } else if (days.toString().isEmpty()) {
            binding.prazoInput.editText?.error = "Coloque o prazo corretamente, por favor!"
        } else if (days.toString().toInt() > 30) {
            binding.prazoInput.editText?.error = "O prazo máximo é de 30 dias!"
        } else if (desc?.isEmpty() == true) {
            binding.descInput.editText?.error = "Coloque a descrição corretamente, por favor!"
        } else if (desc?.length!! < 20) {
            binding.descInput.editText?.error = "Coloque uma descrição maior, por favor!"
        } else if (plataform?.length!! <= 2) {
            binding.plataformaInput.editText?.error =
                "Coloque a plataforma corretamente, por favor!"
        } else {
            val serviceImg: String = when {
                days.toString().toInt() <= 7 -> {
                    meteoro
                }
                days.toString().toInt() in 7..14 -> {
                    estrela
                }
                else -> {
                    lua
                }
            }

            val serviceType: String = when {
                days.toString().toInt() <= 7 -> {
                    "meteoro"
                }
                days.toString().toInt() in 7..14 -> {
                    "estrela"
                }
                else -> {
                    "lua"
                }
            }
            val serviceId = dbRef.push().key!!
            val service = Services(
                serviceId,
                serviceName,
                serviceImg,
                servicePrice,
                serviceDays,
                serviceDesc,
                servicePlataform,
                creatorId,
                serviceType
            )

            dbRef.child(serviceId).setValue(service)
                .addOnCompleteListener {
                    binding.buttonProximo.isClickable = false
                    startActivity(
                        Intent(
                            this@CriarProjeto2Activity,
                            PagamentoProjetoActivity::class.java
                        )
                    )
                }.addOnFailureListener {
                    Toast.makeText(
                        this@CriarProjeto2Activity,
                        "O projeto $serviceName não foi armazenado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            dbRef2.child(serviceId).setValue(service)
                .addOnCompleteListener {
                }.addOnFailureListener {
                    Toast.makeText(
                        this@CriarProjeto2Activity,
                        "O projeto $serviceName não foi armazenado!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
        }
    }

    companion object {
        const val meteoro =
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/meteoro%201%20(2).png?alt=media&token=cb0ec932-e953-4bed-9ced-13e350205855"
        const val estrela =
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/estrela%202.png?alt=media&token=36d1bd5f-4969-4a3a-ad70-0d7a297214fc"
        const val lua =
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/lua-cheia%20(1)%202%20(1).png?alt=media&token=9d385251-b5b4-421f-ad5c-7b4adcaf2a8c"
    }
}

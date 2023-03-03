package com.example.luastation.activities.menusuperior

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.luastation.databinding.CriarProjeto2ScreenBinding
import com.example.luastation.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
            lifecycleScope.launch { saveServiceData() }
        }
    }

    private suspend fun saveServiceData() = coroutineScope {
        val name = binding.nomeProjetoInput.editText!!
        val price = binding.priceInput.editText!!
        val days = binding.prazoInput.editText!!
        val desc = binding.descInput.editText!!
        val plataform = binding.plataformaInput.editText!!

        val serviceImg: String = when {
            days.text.toString().toIntOrNull()!! <= 7 -> {
                meteoro
            }
            days.text.toString().toIntOrNull() in 7..14 -> {
                estrela
            }
            else -> {
                lua
            }
        }

        val serviceType: String = when {
            days.text.toString().toIntOrNull()!! <= 7 -> {
                "meteoro"
            }
            days.text.toString().toIntOrNull() in 7..14 -> {
                "estrela"
            }
            else -> {
                "lua"
            }
        }

        val creatorId = firebaseAuth.currentUser?.uid
        val serviceName = name.text.toString()
        val servicePrice = "R$ " + price.text.toString()
        val serviceDays = days.text.toString() + " dias"
        val serviceDesc = desc.text.toString()
        val servicePlataform = plataform.text.toString()

        if (serviceName.isEmpty()) {
            name.error = "Coloque o nome corretamente, por favor!"
        } else if (servicePrice.isEmpty()) {
            price.error = "Coloque o preço corretamente, por favor!"
        } else if (serviceDays.isEmpty()) {
            days.error = "Coloque o prazo corretamente, por favor!"
        } else if (days.text.toString().toIntOrNull()!! > 30) {
            days.error = "O prazo máximo é de 30 dias!"
        } else if (serviceDesc.isEmpty()) {
            desc.error = "Coloque a descrição corretamente, por favor!"
        } else if (desc.text.length < 20) {
            desc.error = "Coloque uma descrição maior, por favor!"
        } else if (servicePlataform.isEmpty()) {
            plataform.error = "Coloque a plataforma corretamente, por favor!"
        } else {
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
                    finish()
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

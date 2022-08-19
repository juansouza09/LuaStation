package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.CriarProjeto2ScreenBinding
import com.example.luastation.firebase.models.Services
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CriarProjeto2Activity : AppCompatActivity() {
    private lateinit var binding: CriarProjeto2ScreenBinding
    private lateinit var dbRef: DatabaseReference

    private lateinit var name: EditText
    private lateinit var price: EditText
    private lateinit var days: EditText
    private lateinit var plataform: EditText
    private lateinit var desc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CriarProjeto2ScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = binding.nomeProjetoInput.editText!!
        price = binding.priceInput.editText!!
        days = binding.prazoInput.editText!!
        desc = binding.descInput.editText!!
        plataform = binding.plataformaInput.editText!!

        dbRef = FirebaseDatabase.getInstance().getReference("Services")

        binding.icBack.setOnClickListener {
            startActivity(Intent(this, CriarProjetoActivity::class.java))
            finish()
        }

        binding.buttonProximo.setOnClickListener {
            saveServiceData()
        }
    }

    private fun saveServiceData() {

        val serviceName = name.text.toString()
        val servicePrice = "R$" + price.text.toString()
        val serviceDays = days.text.toString() + " dias"
        val serviceDesc = desc.text.toString()
        val servicePlataform = plataform.text.toString()

        if (serviceName.isEmpty()) {
            name.error = "Coloque o nome corretamente, por favor!"
        } else if (servicePrice.isEmpty()) {
            price.error = "Coloque o preço corretamente, por favor!"
        } else if (serviceDays.isEmpty() || days.text.length > 2) {
            days.error = "Coloque o prazo corretamente, por favor!"
        } else if (serviceDesc.isEmpty()) {
            desc.error = "Coloque a descrição corretamente, por favor!"
        } else if (servicePlataform.isEmpty()) {
            plataform.error = "Coloque a plataforma corretamente, por favor!"
        } else {
            val serviceId = dbRef.push().key!!

            val service = Services(
                serviceId,
                serviceName,
                servicePrice,
                serviceDays,
                serviceDesc,
                servicePlataform
            )

            dbRef.child(serviceId).setValue(service)
                .addOnCompleteListener {
                    startActivity(Intent(this, PagamentoProjetoActivity::class.java))
                    finish()
                }.addOnFailureListener { err ->
                    Toast.makeText(
                        this,
                        "O projeto $serviceName não foi armazenado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}

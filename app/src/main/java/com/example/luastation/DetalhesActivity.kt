package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityServicoDetalhesBinding
import com.squareup.picasso.Picasso

class DetalhesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicoDetalhesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicoDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        detalhesIntent()
    }

    private fun detalhesIntent() {
        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aPrice = intent.getStringExtra("iPrice")
        val aDays = intent.getStringExtra("iDays")
        val aPlataform = intent.getStringExtra("iPlataform")
        val aDesc = intent.getStringExtra("iDesc")
        val aImg = intent.getStringExtra("iImg")

        binding.titleTextFreela.text = aTitle
        binding.priceTextFreela.text = aPrice
        binding.timeText.text = aDays
        binding.plataformaText.text = aPlataform
        binding.textDesc.text = aDesc
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
        }
    }
}

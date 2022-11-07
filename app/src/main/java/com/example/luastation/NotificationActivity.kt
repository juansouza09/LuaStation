package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ActivityPerfilUsuarioBinding
import com.example.luastation.databinding.EfetuarProjetoScreenBinding
import com.example.luastation.databinding.NotificationScreenBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: NotificationScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NotificationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dadosIntent()
        listeners()
    }

    private fun dadosIntent() {
        var intent = intent
        val aIdNotification = intent.getStringExtra("iId")
        val aTitle = intent.getStringExtra("iTitle")
        val aDesc = intent.getStringExtra("iDesc")

        binding.nomeProjeto.text = aTitle
        binding.descNotification.text = aDesc

    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

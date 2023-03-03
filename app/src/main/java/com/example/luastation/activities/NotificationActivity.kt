package com.example.luastation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.NotificationScreenBinding

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy {
        NotificationScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dadosIntent()
        setupListeners()
        Toast.makeText(this, "Caso tenha gostado, entre em contato! ;)", Toast.LENGTH_LONG).show()
    }

    private fun dadosIntent() {
        val intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aDesc = intent.getStringExtra("iDesc")
        val aEmail = intent.getStringExtra("iEmail")

        binding.emailAstronauta.text = aEmail
        binding.descNotification.text = aDesc
        binding.titleScreen.text = aTitle

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
}

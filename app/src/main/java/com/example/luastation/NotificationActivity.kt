package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aDesc = intent.getStringExtra("iDesc")
        val aEmail = intent.getStringExtra("iEmail")

        binding.emailAstronauta.text = aEmail
        binding.descNotification.text = aDesc
        binding.titleScreen.text = aTitle

    }

    private fun listeners() {
        binding.icBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

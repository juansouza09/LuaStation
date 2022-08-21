package com.example.luastation.menusuperior

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.luastation.HomeActivity
import com.example.luastation.R
import com.example.luastation.databinding.ProjetoAdicionadoScreenBinding
import java.text.SimpleDateFormat
import java.util.*

class ProjetoAdicionadoActivity : AppCompatActivity() {
    private lateinit var binding: ProjetoAdicionadoScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProjetoAdicionadoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltar.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}

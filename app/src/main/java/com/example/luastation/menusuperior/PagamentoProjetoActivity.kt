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
import com.example.luastation.databinding.ProjetoPagamentoScreenBinding
import java.text.SimpleDateFormat
import java.util.*

class PagamentoProjetoActivity : AppCompatActivity() {

    private val binding by lazy { ProjetoPagamentoScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.icBack.let {
            it.setOnClickListener {
                startActivity(Intent(this, CriarProjeto2Activity::class.java))
                finish()
            }
        }

        binding.buttonFinalizar.let {
            it.setOnClickListener {
                showNotification()
                startActivity(Intent(this, ProjetoAdicionadoActivity::class.java))
                finish()
            }
        }

        binding.btnCancelar.let {
            it.setOnClickListener {
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
        }
    }

    private fun showNotification() {
        createNotificationChannel()

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        val mainIntent = Intent(this, HomeActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent =
            PendingIntent.getActivity(this, 1, mainIntent, PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
        notificationBuilder.setSmallIcon(R.drawable.ic_app)
        notificationBuilder.setContentTitle("Projeto na Lua Station")

        notificationBuilder.setContentText("Parabéns, seu projeto foi publicado com sucesso!")
        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setContentIntent(mainPendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Lua Station Notificação"
            val description = "Descrição da notificação"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "channel01"
    }
}

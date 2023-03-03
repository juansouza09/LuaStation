package com.example.luastation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.NotificationScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy {
        NotificationScreenBinding.inflate(layoutInflater)
    }

    private val database by lazy {
        FirebaseDatabase
            .getInstance()
            .getReference("Notification")
            .child(firebaseAuth.currentUser!!.uid)
    }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dadosIntent()
        setupListeners()
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
        binding.buttonResolver.setOnClickListener {
            setNotificationIsNotActive()
        }
    }

    private fun setNotificationIsNotActive() {
        val notificationId = intent.getStringExtra("iId")
        if (notificationId != null) {
            database
                .child(notificationId)
                .child("isActive")
                .setValue(false)
                .let {
                    it.addOnCompleteListener {
                        binding.buttonResolver.isClickable = false
                        startActivity(Intent(this@NotificationActivity, HomeActivity::class.java))
                        finish()
                    }
                    it.addOnFailureListener {
                    }
                }
        }
        }
    }

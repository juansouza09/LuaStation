package com.example.luastation.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.HomeActivity
import com.example.luastation.databinding.ActivityMenuSeperiorBinding
import com.example.luastation.firebase.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMenuSeperiorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuSeperiorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.linearHomeMenu.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.linearSegurancaMenu.setOnClickListener {
            startActivity(Intent(this, SegurancaActivity::class.java))
            finish()
        }

        binding.linearPubMenu.setOnClickListener {
            startActivity(Intent(this, CriarProjetoActivity::class.java))
            finish()
        }

        binding.linearSairMenu.setOnClickListener {
            checkUser()
            firebaseAuth.signOut()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

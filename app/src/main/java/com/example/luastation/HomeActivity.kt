package com.example.luastation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.luastation.databinding.ActivityHomeBinding
import com.example.luastation.firebase.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val navController: NavController by lazy { findNavController(R.id.navHostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        bottomNavigation()
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val email = firebaseUser.email
            Toast.makeText(this, "Logado como $email", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun bottomNavigation() {
        val bottomNavigation = binding.bottomNavigation

        bottomNavigation.let {
            it.setOnNavigationItemSelectedListener { item ->
                navController.navigate(item.itemId)
                true
            }
            bottomNavigation.setupWithNavController(navController)
        }
    }
}

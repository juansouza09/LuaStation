package com.example.luastation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.luastation.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val navController: NavController by lazy { findNavController(R.id.navHostFragment) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation()
    }

    private fun bottomNavigation() {
        val bottomNavigation = binding.bottomNavigation

        bottomNavigation?.let {
            it.setOnNavigationItemSelectedListener { item ->
                navController.navigate(item.itemId)
                true
            }
            bottomNavigation.setupWithNavController(navController)
        }
    }
}

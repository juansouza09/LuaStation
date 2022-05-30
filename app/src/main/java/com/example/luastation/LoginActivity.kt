package com.example.luastation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luastation.databinding.LoginScreenBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
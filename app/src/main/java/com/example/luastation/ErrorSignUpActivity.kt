package com.example.luastation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.luastation.databinding.ErrorSignupActivityBinding
import com.example.luastation.firebase.login.LoginActivity

class ErrorSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ErrorSignupActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ErrorSignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.let {
            it.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}

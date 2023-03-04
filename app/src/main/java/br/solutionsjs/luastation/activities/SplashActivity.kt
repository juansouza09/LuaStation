package br.solutionsjs.luastation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.SplashActivityBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy {
        SplashActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonEntrar.let {
            it.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}

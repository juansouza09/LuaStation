package br.solutionsjs.luastation.activities.menusuperior

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.solutionsjs.luastation.activities.EquipeActivity
import br.solutionsjs.luastation.activities.HomeActivity
import br.solutionsjs.luastation.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.ActivityMenuSeperiorBinding
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMenuSeperiorBinding.inflate(layoutInflater) }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.linearHomeMenu.let {
            it.setOnClickListener {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        binding.linearSegurancaMenu.let {
            it.setOnClickListener {
                startActivity(Intent(this, SegurancaActivity::class.java))
                finish()
            }
        }

        binding.linearPubMenu.let {
            it.setOnClickListener {
                startActivity(Intent(this, CriarProjetoActivity::class.java))
                finish()
            }
        }

        binding.linearSobreMenu.let {
            it.setOnClickListener {
                startActivity(Intent(this, EquipeActivity::class.java))
                finish()
            }
        }

        binding.linearSairMenu.let {
            it.setOnClickListener {
                checkUser()
                firebaseAuth.signOut()
            }
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

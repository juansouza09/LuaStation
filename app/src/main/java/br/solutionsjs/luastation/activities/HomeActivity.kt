package br.solutionsjs.luastation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.solutionsjs.luastation.R
import br.solutionsjs.luastation.activities.login.LoginActivity
import br.solutionsjs.luastation.databinding.ActivityHomeBinding
import com.google.android.gms.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val navController: NavController by lazy { findNavController(R.id.navHostFragment) }

    private lateinit var mAdView: AdView
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkUser()
        bottomNavigation()
        setupAd()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    private fun setupAd() {
        MobileAds.initialize(this) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Toast.makeText(this@HomeActivity, "Clicou", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                Toast.makeText(this@HomeActivity, "Retorne para o app", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
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

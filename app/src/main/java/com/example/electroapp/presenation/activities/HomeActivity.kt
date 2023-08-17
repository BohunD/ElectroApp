package com.example.electroapp.presenation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electroapp.R
import com.example.electroapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        user?.let{
            startActivity(HomeActivity.newIntent(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(MainActivity.newIntent(this))
        }
    }

    companion object{
        fun newIntent(context: Context): Intent{
            return Intent(context, HomeActivity::class.java)
        }
    }
}
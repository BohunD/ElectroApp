package com.example.electroapp.presenation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electroapp.R
import com.example.electroapp.databinding.ActivityStartBinding
import com.example.electroapp.presenation.fragments.SignInFragment

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, SignInFragment.newInstance())
            .commit()
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, StartActivity::class.java)
        }
    }
}
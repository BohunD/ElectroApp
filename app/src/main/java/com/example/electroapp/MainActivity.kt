package com.example.electroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electroapp.databinding.ActivityMainBinding
import com.example.electroapp.presenation.fragments.SignInFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, SignInFragment.newInstance())
            .commit()
    }
}
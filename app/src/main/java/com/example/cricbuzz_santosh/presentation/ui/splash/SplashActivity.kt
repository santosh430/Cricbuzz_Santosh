package com.example.cricbuzz_santosh.presentation.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.presentation.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed(
             {
                intent=Intent(this,HomeActivity::class.java)
                startActivity(intent)
                 finish()
            },3000
        )
    }
}
package com.example.segregateimagegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scree)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({

           val intent = Intent(SplashScreen@this, GetStarted::class.java)
            startActivity(intent)

        }, 3000)

    }
}
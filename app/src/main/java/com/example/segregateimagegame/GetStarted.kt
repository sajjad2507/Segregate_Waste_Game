package com.example.segregateimagegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

class GetStarted : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        supportActionBar?.hide()

        val getStartedBtn = findViewById<Button>(R.id.getStartedBtn)

        getStartedBtn.setOnClickListener {

            val intent  = Intent(GetStarted@this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}
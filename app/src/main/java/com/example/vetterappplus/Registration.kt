package com.example.vetterappplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val returnLoginR = findViewById<ImageView>(R.id.returnLoginR)

        returnLoginR.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
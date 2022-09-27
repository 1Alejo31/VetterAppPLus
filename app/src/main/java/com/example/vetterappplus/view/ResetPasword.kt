package com.example.vetterappplus.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.vetterappplus.R

class ResetPasword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pasword)


        val returnLogin = findViewById<ImageView>(R.id.returnLogin)

        returnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
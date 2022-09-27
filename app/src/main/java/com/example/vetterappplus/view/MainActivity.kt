package com.example.vetterappplus.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.vetterappplus.R

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val resetPasword = findViewById<Button>(R.id.resetPassword)
        val uiRegister = findViewById<Button>(R.id.register)

        resetPasword.setOnClickListener {
            startActivity(Intent(this, ResetPasword::class.java))
        }

        uiRegister.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }


    }
}
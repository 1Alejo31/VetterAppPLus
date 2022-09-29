package com.example.vetterappplus.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vetterappplus.R

class Home : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        this.supportActionBar?.hide()

        val reciclerView = findViewById<RecyclerView>(R.id.reciclerView)
        val adapter = CustomAdapter()

            reciclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        reciclerView.adapter = adapter

    }



}
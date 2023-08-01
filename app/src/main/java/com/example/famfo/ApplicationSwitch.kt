package com.example.famfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ApplicationSwitch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_switch)

        val famfo : Button = findViewById(R.id.famfo)
        val calci : Button = findViewById(R.id.calculator)

        famfo.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        calci.setOnClickListener{
            val intent = Intent(this, Calculator::class.java)
            startActivity(intent)
        }

    }
}
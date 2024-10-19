package com.example.myapp005moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val twInfo = findViewById<TextView>(R.id.twInfo)
        //Načtení dat z intentu

        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Data z první aktivity. Jméno: $nickname"

        val etReasons = findViewById<EditText>(R.id.etReasons)



        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener{
        finish()
            }

        val btnThird = findViewById<Button>(R.id.btnThird)
        btnThird.setOnClickListener {
            val reasons = etReasons.text.toString()
            val intent = Intent (this, ThirdActivity::class.java)
            intent.putExtra("REASONS", reasons)
            startActivity(intent)

        }
    }
}
package com.example.myapp005moreactivities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_third)

        val twInfo2 = findViewById<TextView>(R.id.twInfo2)

        val reasons = intent.getStringExtra("REASONS")
        twInfo2.text = "Data ze druhé aktivity. Důvod, proč vás lidi mají oslovovat touto přezdívkou: $reasons"

        val btnBack2 = findViewById<Button>(R.id.btnBack2)
        btnBack2.setOnClickListener{
            finish()
        }
    }
}
package com.example.myapp002myownlinearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets*/


            val etName = findViewById<EditText>(R.id.etName)
            val etName1 = findViewById<EditText>(R.id.etName1)
            val etName3 = findViewById<EditText>(R.id.etName3)
            val etDate = findViewById<EditText>(R.id.etDate)
            val tvInformation = findViewById<TextView>(R.id.tvInformation)
            val btnSend = findViewById<Button>(R.id.btnSend)
            val btnDelete = findViewById<Button>(R.id.btnDelete)

            // nastavení obsluhy pro tlačítko Odeslat

            btnSend.setOnClickListener{
                val name = etName.text.toString()
                val name1 = etName1.text.toString()
                val name3 = etName3.text.toString()
                val date = etDate.text.toString()

                // Zobrazen textu v TextView
                val formattedText = "Název poznámky: $name" + System.getProperty("line.separator") +
                        "Od: $name1" + System.getProperty("line.separator") + "Datum: $date" + System.getProperty("line.separator") + "$name3"
                tvInformation.text = formattedText

            }

            btnDelete.setOnClickListener{
                etName.text.clear()
                etName1.text.clear()
                etDate.text.clear()
                etName3.text.clear()
            tvInformation.text = ""
            }
    }
}


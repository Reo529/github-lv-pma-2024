package com.example.myapp005moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val btnSend = findViewById<Button>(R.id.btnSend)
        val etNickname = findViewById<EditText>(R.id.etNickname)

        btnSend.setOnClickListener{
            val nickname = etNickname.text.toString() //Získáme text z edit text pole
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            startActivity(intent)

        }
    }
}
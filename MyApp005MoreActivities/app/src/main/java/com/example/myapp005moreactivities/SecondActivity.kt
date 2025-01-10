package com.example.myapp005moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005moreactivities.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val twInfo = binding.twInfo
        //Načtení dat z intentu

        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Jméno: $nickname"


        binding.btnBack.setOnClickListener{
        finish()
            }

        binding.btnThird.setOnClickListener {
            val reasons = binding.etReasons.text.toString()
            val intent = Intent (this, ThirdActivity::class.java)
            intent.putExtra("REASONS", reasons)
            startActivity(intent)

        }

        setSupportActionBar(binding.topbar)
        supportActionBar?.title = "Moje přezdívka"
        binding.topbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))


    }
}
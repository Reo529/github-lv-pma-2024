package com.example.myapp016avanocniappka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.myapp016avanocniappka.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCreate.setOnClickListener {
            val wish = binding.etWish.text.toString() //Získáme text z edit text pole
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NICK_NAME", wish)
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            Snackbar.make(it, "Historie přání bude dostupná brzy!", Snackbar.LENGTH_LONG).show()
        }

        setSupportActionBar(binding.topbar)
        supportActionBar?.title = "Vánoční přání"
        binding.topbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

    }


}
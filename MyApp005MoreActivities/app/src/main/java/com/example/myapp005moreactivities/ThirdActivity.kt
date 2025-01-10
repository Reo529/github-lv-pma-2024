package com.example.myapp005moreactivities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005moreactivities.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val reasons = intent.getStringExtra("REASONS")
        binding.twInfo2.text = "Vaše důvody: $reasons"

        binding.btnBack2.setOnClickListener{
            finish()
        }

        setSupportActionBar(binding.topbar)
        supportActionBar?.title = "Moje přezdívka"
        binding.topbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))

    }
}
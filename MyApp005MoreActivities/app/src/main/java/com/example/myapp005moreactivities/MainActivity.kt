package com.example.myapp005moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005moreactivities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicializace viewbinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSend.setOnClickListener{
            val nickname = binding.etNickname.text.toString() //Získáme text z edit text pole
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            startActivity(intent)

            setSupportActionBar(binding.topbar)
            supportActionBar?.title = "Moje přezdívka"
            binding.topbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))

        }
    }
}
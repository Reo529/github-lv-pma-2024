package com.example.myapp016avanocniappka

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp016avanocniappka.databinding.ActivityCardBinding

class CardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Získání dat z intentu
        val wish = intent.getStringExtra("WISH")
        binding.twWish.text = "$wish"
        val imageUri = intent.getStringExtra("IMAGE_URI")

        if (wish != null && imageUri != null) {
            // Zobrazení textu a obrázku
            binding.twWish.text = wish
            binding.ivWishImage.setImageURI(Uri.parse(imageUri))
        } else {
            finish()
        }
    }
}
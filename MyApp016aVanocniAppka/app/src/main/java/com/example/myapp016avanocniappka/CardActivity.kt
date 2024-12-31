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
        val imageUri = intent.getStringExtra("IMAGE_URI")

        if (wish != null && imageUri != null) {
            // Zobrazení textu a obrázku
            binding.twWish.text = wish
            binding.ivWishImage.setImageURI(Uri.parse(imageUri))
        } else {
            // Pokud data chybí, zobrazíme zprávu a aktivitu ukončíme
            Toast.makeText(this, "Data pro přání nebo obrázek nejsou dostupná.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
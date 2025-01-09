package com.example.myapp016avanocniappka

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp016avanocniappka.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val wish = intent.getStringExtra("WISH")
        binding.twInfo.text = "Vaše přání: $wish"

        // Nastavení toolbaru
        setSupportActionBar(binding.topbar)
        supportActionBar?.title = "Vánoční přání"
        binding.topbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

        // Aktivace vybrání obrázku
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Nastavení obrázku do ImageView a uložením URI do tagu
            uri?.let {
                binding.ivImage.setImageURI(it)
                binding.ivImage.tag = it // Uložení URI do tagu
            }
        }

        binding.btnTakeImage.setOnClickListener {
            getContent.launch("image/*") // Spustí volbu obrázku
        }


        // Akce pro návrat zpět
        binding.btnBack.setOnClickListener {
            finish() // Ukončí aktivitu
        }

        // Akce pro vytvoření přání
        binding.btnCreateCard.setOnClickListener {
            val imageUri = binding.ivImage.tag as? Uri // Získání URI z tagu ImageView
            if (imageUri != null) {
                val intent = Intent(this, CardActivity::class.java).apply {
                    putExtra("WISH", wish) // Předání textu přání
                    putExtra("IMAGE_URI", imageUri.toString()) // Předání URI obrázku jako String
                }
                startActivity(intent) // Spuštění nové aktivity
            } else {
                // Zobrazí Toast, pokud nebyl obrázek vybrán
                Toast.makeText(this, "Nejprve vyberte obrázek!", Toast.LENGTH_SHORT).show()
            }
        }
        setSupportActionBar(binding.topbar)
        supportActionBar?.title = "Vánoční přání"
        binding.topbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

    }
    }

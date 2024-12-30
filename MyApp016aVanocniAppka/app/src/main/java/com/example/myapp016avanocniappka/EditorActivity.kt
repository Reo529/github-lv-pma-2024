package com.example.myapp016avanocniappka

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp016avanocniappka.databinding.ActivityEditorBinding
import com.example.myapp016avanocniappka.databinding.ActivityMainBinding


class EditorActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etMessage = findViewById<EditText>(R.id.etMessage)


        binding.btnSave.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotBlank()) {
                saveMessage(message)
                Toast.makeText(this, "Přání uloženo!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Text přání nemůže být prázdný!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShare.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotBlank()) {
                shareMessage(message)
            } else {
                Toast.makeText(this, "Text přání nemůže být prázdný!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveMessage(message: String) {
        val sharedPref = getSharedPreferences("VanocniPozdravy", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("last_message", message)
        editor.apply()
    }

    private fun shareMessage(message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Moje vánoční přání: $message")
        startActivity(Intent.createChooser(shareIntent, "Sdílet přání"))
    }
}
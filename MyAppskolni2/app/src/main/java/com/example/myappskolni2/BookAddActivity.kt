package com.example.myappskolni2

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myappskolni2.databinding.AddBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookAddActivity : AppCompatActivity() {
    lateinit var binding: AddBookBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Spinner for category selection
        val categories = listOf("Fiction", "Science", "History", "Mystery")
        val spinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = spinnerAdapter

        // Handle the Save button click
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val author = binding.etAuthor.text.toString()

            // Validate input and save book
            if (title.isNotEmpty() && author.isNotEmpty()) {
                saveBook(title, author)
            } else {
                Toast.makeText(this, "Please fill in both title and author.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBook(title: String, author: String) {
        // Logic to save the book (e.g., database, shared preferences, etc.)
        Toast.makeText(this, "Book Saved: $title by $author", Toast.LENGTH_SHORT).show()
        finish() // Finish activity after saving the book
    }
}



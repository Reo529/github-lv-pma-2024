package com.example.myapp014amynotehub

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp014amynotehub.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var database: NoteHubDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializace databáze
        database = NoteHubDatabaseInstance.getDatabase(this)
        insertDefaultCategories()
        insertDefaultTags()

        // Inicializace RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //noteAdapter = NoteAdapter(getSampleNotes())

        //noteAdapter = NoteAdapter(emptyList()) // Inicializace s prázdným seznamem
        //binding.recyclerView.adapter = noteAdapter

        binding.fabAddNote.setOnClickListener {
            showAddNoteDialog()
        }

        // Vložení testovacích dat
        // insertSampleNotes()

        // Načtení poznámek z databáze
        loadNotes()

    }

    private fun showAddNoteDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_note, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Přidat poznámku")
            .setView(dialogView)
            .setPositiveButton("Přidat") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                addNoteToDatabase(title, content)
            }
            .setNegativeButton("Zrušit", null)
            .create()

        dialog.show()
    }

    private fun addNoteToDatabase(title: String, content: String) {
        lifecycleScope.launch {
            val newNote = Note(title = title, content = content)
            database.noteDao().insert(newNote)  // Vloží poznámku do databáze
            loadNotes()  // Aktualizuje seznam poznámek
        }
    }

    private fun loadNotes() {
        lifecycleScope.launch {
            database.noteDao().getAllNotes().collect { notes ->
                noteAdapter = NoteAdapter(notes, onDeleteClick = { note ->
                    deleteNote(note)
                })
                binding.recyclerView.adapter = noteAdapter
            }
        }
    }

    private fun insertSampleNotes() {
        lifecycleScope.launch {
            val sampleNotes = listOf(
                Note(title = "Vzorek 1", content = "Obsah první testovací poznámky"),
                Note(title = "Vzorek 2", content = "Obsah druhé testovací poznámky"),
                Note(title = "Vzorek 3", content = "Obsah třetí testovací poznámky")
            )
            sampleNotes.forEach { database.noteDao().insert(it) }
        }
    }

    private fun insertDefaultCategories() {
        lifecycleScope.launch {
            val existingCategories = database.categoryDao().getAllCategories().first()  // Použití first() pro získání seznamu
            if (existingCategories.isEmpty()) {
                val defaultCategories = listOf(
                    Category(name = "Práce"),
                    Category(name = "Osobní"),
                    Category(name = "Nápady")
                )
                defaultCategories.forEach { database.categoryDao().insert(it) }
            }
        }
    }

    private fun insertDefaultTags() {
        lifecycleScope.launch {
            val existingTags = database.tagDao().getAllTags().first()  // Použití first() pro získání seznamu
            if (existingTags.isEmpty()) {
                val defaultTags = listOf(
                    Tag(name = "Důležité"),
                    Tag(name = "Rychlé úkoly"),
                    Tag(name = "Projekt"),
                    Tag(name = "Nápad")
                )
                defaultTags.forEach { database.tagDao().insert(it) }
            }
        }
    }
    private fun deleteNote(note: Note) {
        lifecycleScope.launch {
            database.noteDao().delete(note)  // Smazání poznámky z databáze
            loadNotes()  // Aktualizace seznamu poznámek
        }
    }



    /*private fun getSampleNotes(): List<Note> {
        // Testovací seznam poznámek
        return listOf(
            Note(title = "Poznámka 1", content = "Obsah první poznámky"),
            Note(title = "Poznámka 2", content = "Obsah druhé poznámky"),
            Note(title = "Poznámka 3", content = "Obsah třetí poznámky")
        )
    }*/


}
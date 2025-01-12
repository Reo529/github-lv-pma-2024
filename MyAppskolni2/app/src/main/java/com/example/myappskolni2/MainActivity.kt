package com.example.myappskolni2

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappskolni2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // Přidání proměnných pro filtrování a řazení
    private var isNameAscending = true // Pro sledování stavu řazení podle názvu
    private var currentCategory: String = "Vše" // Aktuálně vybraná kategorie
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Inicializace databáze
        database = AppDatabaseInstance.getDatabase(this)
        insertDefaultCategories()
        setupUI()
        // zakomentovat protože to tam nemá

        // Inicializace RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //noteAdapter = NoteAdapter(getSampleNotes())

        //noteAdapter = NoteAdapter(emptyList()) // Inicializace s prázdným seznamem
        //binding.recyclerView.adapter = noteAdapter

        binding.fabAddBook.setOnClickListener {
            showAddBookDialog()
        }

        // Vložení testovacích dat
        // insertSampleNotes()ddddd

        // Načtení poznámek z databáze
        loadBooks()


    }

    private fun showAddBookDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_book, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.etTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)


        // Načtení kategorií z databáze a jejich zobrazení ve Spinneru
        lifecycleScope.launch {
            val categories = database.categoryDao().getAllCategories().first()  // Načteme kategorie
            val categoryNames = categories.map { it.name }  // Převedeme na seznam názvů kategorií
            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Přidat poznámku")
            .setView(dialogView)
            .setPositiveButton("Přidat") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                val selectedCategory = spinnerCategory.selectedItem.toString()  // Získáme vybranou kategorii

                // Najdeme ID vybrané kategorie
                lifecycleScope.launch {
                    val category = database.categoryDao().getCategoryByName(selectedCategory)
                    if (category != null) {
                        addBookToDatabase(title, content, category.id)
                    }
                }
            }
            .setNegativeButton("Zrušit", null)
            .create()

        dialog.show()
    }

    private fun addBookToDatabase(title: String, author: String, categoryId: Int) {
        lifecycleScope.launch {
            val newNote = Book(title = title, author = author, categoryId = categoryId)
            database.noteDao().insert(newNote)  // Vloží poznámku do databáze
            loadNotes()  // Aktualizuje seznam poznámek
        }
    }

    private fun loadNotes() {
        lifecycleScope.launch {
            var notes = if (currentCategory == "Vše") {
                database.bookDao().getAllNotes().first()
            } else {
                val category = database.categoryDao().getCategoryByName(currentCategory)
                if (category != null) {
                    database.bookDao().getBooksByCategoryId(category.id).first()
                } else {
                    emptyList()
                }
            }

            // Aplikujeme řazení podle názvu
            if (isNameAscending) {
                books = books.sortedWith(compareBy { it.title?.lowercase() ?: "" }) // Ignorujeme velká/malá písmena
            } else {
                books = books.sortedWith(compareByDescending { it.title?.lowercase() ?: "" })
            }

            // Aktualizace RecyclerView
            bookAdapter = BookAdapter(
                books = books,
                onDeleteClick = { note -> deleteBook(book) },
                onEditClick = { note -> editBook(book) },
                lifecycleScope = lifecycleScope,
                database = database
            )
            binding.recyclerView.adapter = bookAdapter
        }
    }

    private fun insertSampleBooks() {
        lifecycleScope.launch {
            val sampleBooks = listOf(
                Book(title = "Vzorek 1", author = "Obsah první testovací poznámky"),
                Book(title = "Vzorek 2", author = "Obsah druhé testovací poznámky"),
                Book(title = "Vzorek 3", author = "Obsah třetí testovací poznámky")
            )
            sampleBooks.forEach { database.noteDao().insert(it) }
        }
    }

    private fun insertDefaultCategories() {
        lifecycleScope.launch {
            val defaultCategories = listOf(
                "Osobní",
                "Práce",
                "Nápady"
            )

            for (categoryName in defaultCategories) {
                val existingCategory = database.categoryDao().getCategoryByName(categoryName)
                if (existingCategory == null) {
                    // Kategorie s tímto názvem neexistuje, vložíme ji
                    database.categoryDao().insert(Category(name = categoryName))
                }
            }
        }
    }


    private fun deleteBook(book: Book) {
        lifecycleScope.launch {
            database.bookDao().delete(book)  // Smazání poznámky z databáze
            loadBooks()  // Aktualizace seznamu poznámek
        }
    }

    private fun editNote(book: Book) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_note, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)

        // Načtení kategorií z databáze a jejich zobrazení ve Spinneru
        lifecycleScope.launch {
            val categories = database.categoryDao().getAllCategories().first()  // Načteme kategorie
            val categoryNames = categories.map { it.name }  // Převedeme na seznam názvů kategorií
            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter

            // Nastavení výchozí kategorie (aktivní)
            val noteCategoryId = book.categoryId
            if (noteCategoryId != null) {
                val currentCategory = categories.firstOrNull { it.id == noteCategoryId }
                val categoryIndex = currentCategory?.let { categoryNames.indexOf(it.name) } ?: 0
                spinnerCategory.setSelection(categoryIndex)
            }
        }


        // Předvyplnění stávajících dat poznámky
        titleEditText.setText(book.title)
        contentEditText.setText(book.author)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Upravit knihu")
            .setView(dialogView)
            .setPositiveButton("Uložit") { _, _ ->
                val updatedTitle = titleEditText.text.toString()
                val updatedContent = contentEditText.text.toString()
                val selectedCategory = spinnerCategory.selectedItem.toString()  // Získáme vybranou kategorii


                // Aktualizace poznámky v databázi
                lifecycleScope.launch {
                    val category = database.categoryDao().getCategoryByName(selectedCategory)
                    if (category != null) {
                        val updatedNote = book.copy(
                            title = updatedTitle,
                            categoryId = category.id  // Nastavíme ID vybrané kategorie
                        )
                        database.noteDao().update(updatedNote)  // Uloží aktualizovanou poznámku
                        loadNotes()  // Načte a aktualizuje seznam poznámek
                    }
                }
            }
            .setNegativeButton("Zrušit", null)
            .create()

        dialog.show()
    }

    private fun clearDatabase() {
        lifecycleScope.launch {
            // Smazání všech poznámek
            database.noteDao().deleteAllNotes()

            // Smazání všech kategorií
            database.categoryDao().deleteAllCategories()

            // Resetování auto-increment hodnoty
            resetAutoIncrement("note_table")
            resetAutoIncrement("category_table")
        }
    }

    private fun resetAutoIncrement(tableName: String) {
        lifecycleScope.launch {
            database.openHelper.writableDatabase.execSQL("DELETE FROM sqlite_sequence WHERE name = '$tableName'")
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
    private fun setupUI() {
        setupFilterSpinner()
        setupSortButtons()
    }

    private fun setupFilterSpinner() {
        lifecycleScope.launch {
            val categories = database.categoryDao().getAllCategories().first()
            val categoryNames = categories.map { it.name }.toMutableList()
            categoryNames.add(0, "Vše") // Přidáme možnost "Vše"

            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerFilterCategory.adapter = adapter

            // Nastavení OnItemSelectedListener
            binding.spinnerFilterCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    currentCategory = categoryNames[position]
                    loadBooks() // Načte poznámky podle vybrané kategorie
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Není třeba nic dělat, když není nic vybráno
                }
            }
        }
    }

    private fun setupSortButtons() {
        binding.btnSortByName.setOnClickListener {
            isNameAscending = !isNameAscending
            loadBooks()
        }
    }



}

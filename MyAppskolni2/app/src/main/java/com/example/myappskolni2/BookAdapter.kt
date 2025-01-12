package com.example.myappskolni2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.myappskolni2.databinding.ItemBookBinding
import kotlinx.coroutines.launch


class BookAdapter(
    private val lifecycleScope: LifecycleCoroutineScope,  // Přidán lifecycleScope
    private val database: AppDatabase,  // Instance databáze
    private val books: List<Book>,  // Seznam knih
    private val onDeleteClick: (Book) -> Unit,  // Funkce pro mazání knihy
    private val onEditClick: (Book) -> Unit     // Funkce pro editaci knihy
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.noteTitle.text = book.title
            binding.noteContentPreview.text = book.author

            // Ověření, zda categoryId není null
            val categoryId = book.categoryId
            if (categoryId != null) {
                lifecycleScope.launch {
                    val category = database.categoryDao().getCategoryById(categoryId)
                    binding.noteCategory.text = category?.name ?: "Neznámá kategorie"
                }
            } else {
                binding.noteCategory.text = "Bez kategorie"
            }

            // Kliknutí na ikonu pro mazání
            binding.iconDelete.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle("Smazat knihu")
                    .setMessage("Opravdu chcete tuto knihu smazat?")
                    .setPositiveButton("Ano") { _, _ ->
                        onDeleteClick(book)  // Vyvolání funkce pro mazání knihy
                    }
                    .setNegativeButton("Ne", null)
                    .show()
            }

            // Kliknutí na ikonu pro editaci
            binding.iconEdit.setOnClickListener {
                onEditClick(book)  // Vyvolání funkce pro editaci knihy
            }
        }
    }
}
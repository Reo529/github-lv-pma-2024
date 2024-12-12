package com.example.myapp007bfragmentsexample1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Přidáme ListFragment, pokud ještě neexistuje
        if (savedInstanceState == null) {
            val listFragment = ListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_list, listFragment)
                .commit()
        }
    }

    // Voláno při výběru knihy
    fun onBookSelected(title: String, author: String) {
        // Vytvoření nového DetailFragment a nastavení argumentů
        val detailFragment = DetailFragment()

        val imageResIds = arrayOf(
            R.drawable.baseline_memory_24,  // Představuje první obrázek
            R.drawable.ic_launcher_foreground,  // Představuje druhý obrázek
            R.drawable.baseline_design_services_24   // Představuje třetí obrázek
        )

        val randomImageResId = imageResIds.random()  // Generuje náhodný obrázek

        val bundle = Bundle().apply {
            putString("title", title)
            putString("author", author)
            putInt("imageResId", randomImageResId)  // Uložení náhodného obrázku
        }
        detailFragment.arguments = bundle

        // Nahradíme starý fragment novým a commitneme transakci
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_detail, detailFragment)
            .commit()
    }
}

package com.example.myapp09adatastore

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapp09adatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    private val NAME_KEY = stringPreferencesKey("name")
    private val AGE_KEY = intPreferencesKey("age")
    private val IS_ADULT_KEY = booleanPreferencesKey("isAdult")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageString = binding.etAge.text.toString().trim()

            if (ageString.isBlank()) {
                Toast.makeText(this, "Hele, vyplň věk...", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageString.toInt()
                val isAdult = binding.cbAdult.isChecked
                if ((age < 18 && isAdult) || (age >= 18 && !isAdult)) {
                    Toast.makeText(this, "Kecáš, takže nic ukládat nebudu", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Jasně, ukládám...", Toast.LENGTH_SHORT).show()

                    // Uložíme data do DataStore
                    lifecycleScope.launch {
                        saveData(name, age, isAdult)
                    }
                }
            }
        }

        // Funkcionalita pro načtení dat
        binding.btnLoad.setOnClickListener {
            loadData()
        }
    }

    // Funkce pro uložení dat do DataStore
    private suspend fun saveData(name: String, age: Int, isAdult: Boolean) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[AGE_KEY] = age
            preferences[IS_ADULT_KEY] = isAdult
        }
    }

    // Funkce pro načtení dat z DataStore a aktualizaci UI
    private fun loadData() {
        val nameFlow: Flow<String?> = dataStore.data.map { preferences ->
            preferences[NAME_KEY]
        }

        val ageFlow: Flow<Int> = dataStore.data.map { preferences ->
            preferences[AGE_KEY] ?: 0
        }

        val isAdultFlow: Flow<Boolean> = dataStore.data.map { preferences ->
            preferences[IS_ADULT_KEY] ?: false
        }

        // Použijeme lifecycleScope k asynchronnímu načítání dat
        lifecycleScope.launch {
            nameFlow.collect { name ->
                binding.etName.setText(name)
            }

            ageFlow.collect { age ->
                binding.etAge.setText(age.toString())
            }

            isAdultFlow.collect { isAdult ->
                binding.cbAdult.isChecked = isAdult
            }
        }
    }
}
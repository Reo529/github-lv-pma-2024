package com.example.myapp009skolni

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapp009skolni.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    // Definování DataStore pro ukládání preferencí
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    // Klíče pro ukládání do DataStore
        val nameKey = stringPreferencesKey("name")
        val ageKey = intPreferencesKey("age")
        val isAdultKey = booleanPreferencesKey("isAdult")

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

                    // Ukládání do DataStore
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.edit { preferences ->
                            preferences[nameKey] = name
                            preferences[ageKey] = age
                            preferences[isAdultKey] = isAdult
                        }
                    }
                }
            }
        }

        binding.btnLoad.setOnClickListener {
            // Načítání z DataStore
            CoroutineScope(Dispatchers.IO).launch {
                val preferences = dataStore.data.first()
                val name = preferences[nameKey] ?: ""
                val age = preferences[ageKey] ?: 0
                val isAdult = preferences[isAdultKey] ?: false

                // Aktualizace UI na hlavním vlákně
                runOnUiThread {
                    binding.etName.setText(name)
                    binding.etAge.setText(age.toString())
                    binding.cbAdult.isChecked = isAdult
                }
            }
        }
    }
}
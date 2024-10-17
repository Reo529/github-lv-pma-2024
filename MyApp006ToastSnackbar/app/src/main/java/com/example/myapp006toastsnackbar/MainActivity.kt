package com.example.myapp006toastsnackbar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp006toastsnackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicializace viewbinding
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Nastavení akce pro tlačítko TOAST
        binding.btnShowToast.setOnClickListener {
        val toast = Toast.makeText( this, "Nazdar - mám hlad", Toast.LENGTH_LONG)
        toast.show()
        }

        binding.btnShowSnackbar.setOnClickListener {

            Snackbar.make(binding.root, "Já jsem asi SNACKBAR", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.parseColor("#FF4545"))
                .setActionTextColor(Color.WHITE)
                .setDuration(5000)
                .setAction("Zavřít") {
                    Toast.makeText(this, "Zavírám SNACKBAR", Toast.LENGTH_SHORT).show()
                }
                .show()


        }
    }
}
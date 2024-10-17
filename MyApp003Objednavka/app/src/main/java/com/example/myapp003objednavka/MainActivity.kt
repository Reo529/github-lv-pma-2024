package com.example.myapp003objednavka

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp003objednavka.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Nastavení akce pro tlačítko TOAST
        binding.btnShowToast.setOnClickListener {
        val toast = Toast.makeText( this, "Záložka uložena", Toast.LENGTH_LONG)
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

        title = "Objednávka sedla"

        binding.btnObjednavka.setOnClickListener {

            //načtení ID vybraného radioButtonu z radioGroup
            val rgSedla = binding.rgSedla.checkedRadioButtonId

            val sedlo = findViewById<RadioButton>(rgSedla)


            val kuze = binding.cbKuze.isChecked
            val krem = binding.cbKremnakuzi.isChecked
            val skluz = binding.cbProtiskluz.isChecked

            val objednavkaText = "Souhrn objednávky: " +
                    "${sedlo.text}" +
                    (if(kuze) "; lepší kůže" else "") +
                    (if(krem) "; krém na kůži" else "") +
                    (if(skluz) "; protiskluzové mazání" else "")


            binding.tvObjednavka.text = objednavkaText

        }

        //změna obrázků v závislosti na vybraném radioButtonu

        binding.rbSedlo1.setOnClickListener {
            binding.ivSedlo.setImageResource(R.drawable.sedlo_1)
                         }
        binding.rbSedlo2.setOnClickListener {
            binding.ivSedlo.setImageResource(R.drawable.sedlo_2)
        }
        binding.rbSedlo3.setOnClickListener {
            binding.ivSedlo.setImageResource(R.drawable.sedlo_3)
        }

    }
    }

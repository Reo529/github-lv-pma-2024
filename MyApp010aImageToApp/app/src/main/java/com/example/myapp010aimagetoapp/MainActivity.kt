package com.example.myapp010aimagetoapp

import android.graphics.ColorMatrix
import android.graphics.Bitmap
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp010aimagetoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                binding.ivImage.setImageURI(uri)
            }
        binding.btnTakeImage.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.btnApplyFilter.setOnClickListener {
            applyBlackAndWhiteFilter()

        }
        binding.btnRemoveFilter.setOnClickListener {
            removeFilter()
            }
        binding.ivRotace.setOnClickListener {
            rotateImage()
        }

    }

    private fun applyBlackAndWhiteFilter() {
        val imageView = binding.ivImage
        val bitmap = (imageView.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap

        bitmap?.let {
            // Aplikování černobílého filtru
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f) // Černobílý filtr
            val filter = ColorMatrixColorFilter(colorMatrix)

            // Nastavení filtru na obrázek
            imageView.colorFilter = filter
        }
    }
        private fun removeFilter () {
        val imageView = binding.ivImage
        imageView.colorFilter = null  // Odstraní jakýkoli aplikovaný filtr
    }

// Funkce pro otáčení obrázku o 90 stupňů
private fun rotateImage() {
    val imageView = binding.ivImage
    val bitmap = (imageView.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap

    bitmap?.let {
        val matrix = android.graphics.Matrix()
        matrix.postRotate(90f)  // Otočení o 90 stupňů
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        imageView.setImageBitmap(rotatedBitmap)
        }
    }
}
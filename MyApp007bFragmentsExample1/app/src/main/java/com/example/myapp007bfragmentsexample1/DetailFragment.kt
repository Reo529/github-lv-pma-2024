package com.example.myapp007bfragmentsexample1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DetailFragment : Fragment() {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewAuthor: TextView
    private lateinit var imageViewDetail: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        textViewTitle = view.findViewById(R.id.textViewTitle)
        textViewAuthor = view.findViewById(R.id.textViewAuthor)
        imageViewDetail = view.findViewById(R.id.imageViewDetail)

        // Načtení argumentů a aktualizace textových polí
        arguments?.let {
            val title = it.getString("title")
            val author = it.getString("author")
            val imageResId = it.getInt("imageResId")
            updateDetails(title ?: "Unknown", author ?: "Unknown", imageResId)

        }

        return view
    }

    // Metoda pro aktualizaci zobrazení detailů
    fun updateDetails(title: String, author: String, imageResId: Int) {
        textViewTitle.text = title
        textViewAuthor.text = author
        imageViewDetail.setImageResource(imageResId)
    }
}
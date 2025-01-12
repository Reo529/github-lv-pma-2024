package com.example.semestralni_projekt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")

data class Book (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val comment: String,
    val categoryId: Int? = null,  // Volitelný odkaz na kategorii
    val isRead: Boolean = false,
    val rating: Float = 0f
)
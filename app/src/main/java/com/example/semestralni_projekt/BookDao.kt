package com.example.semestralni_projekt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {


    // Vloží novou knihu do databáze
    @Insert
    suspend fun insert(book: Book)

    // Aktualizuje existující knihu
    @Update
    suspend fun update(book: Book)

    // Smaže zadanou knihu
    @Delete
    suspend fun delete(book: Book)

    // Načte všechny knihy a vrátí je jako Flow, které umožňuje pozorování změn
    @Query("SELECT * FROM book_table ORDER BY id DESC")
    fun getAllBooks(): Flow<List<Book>>

    // Vymaže všechny knihy z tabulky
    @Query("DELETE FROM book_table")
    suspend fun deleteAllBooks()

    // Načte knihy podle ID kategorie a vrátí je jako Flow
    @Query("SELECT * FROM book_table WHERE categoryId = :categoryId")
    fun getBooksByCategoryId(categoryId: Int): Flow<List<Book>>
}
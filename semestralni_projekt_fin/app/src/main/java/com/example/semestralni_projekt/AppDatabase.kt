package com.example.semestralni_projekt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class, Category::class],
    version = 4
)

//@Database(
//  entities = [Book::class, Category::class],
//version = 1

//)
abstract class AppDatabase : RoomDatabase() {


    abstract fun categoryDao(): CategoryDao
    abstract fun bookDao(): BookDao
}
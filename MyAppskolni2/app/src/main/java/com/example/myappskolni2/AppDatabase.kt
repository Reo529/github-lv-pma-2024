package com.example.myappskolni2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class, Category::class],
    version = 1

)
abstract class AppDatabase : RoomDatabase() {


    abstract fun categoryDao(): CategoryDao
    abstract fun bookDao(): BookDaor
}

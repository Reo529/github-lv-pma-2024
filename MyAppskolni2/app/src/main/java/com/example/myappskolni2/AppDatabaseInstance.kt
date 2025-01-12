package com.example.myappskolni2


import android.content.Context
import androidx.room.Room

object AppDatabaseInstance {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "book_database"
            ).build()
            INSTANCE = instance
            instance
        }

    }
}
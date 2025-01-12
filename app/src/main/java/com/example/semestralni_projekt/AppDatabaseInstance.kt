package com.example.semestralni_projekt

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDatabaseInstance {

    @Volatile
    private var INSTANCE: AppDatabase? = null
    // Definice migrace
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Přidání nového sloupce 'comment' do tabulky 'book_table'
            database.execSQL("ALTER TABLE book_table ADD COLUMN comment TEXT")
        }
    }
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .addMigrations(MIGRATION_1_2) // Přidáme migraci
                .build()
            INSTANCE = instance
            instance
        }
    }

}

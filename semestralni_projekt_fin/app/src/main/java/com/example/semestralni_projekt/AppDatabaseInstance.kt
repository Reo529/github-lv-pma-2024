package com.example.semestralni_projekt

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDatabaseInstance {

    @Volatile
    private var INSTANCE: AppDatabase? = null


    // Migrace 4 -> 5 (přidání sloupce 'startDate')
    private val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Přidání sloupce startDate do tabulky book_table
            database.execSQL("ALTER TABLE book_table ADD COLUMN startDate TEXT")
        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE book_table ADD COLUMN rating REAL NOT NULL DEFAULT 0")
        }
    }

    // Migrace 1 -> 2 (přidání sloupce 'comment')
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE book_table ADD COLUMN comment TEXT")
        }
    }

    // Migrace 2 -> 3 (přidání sloupce 'isRead')
    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE book_table ADD COLUMN isRead INTEGER NOT NULL DEFAULT 0")
        }
    }

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3,MIGRATION_3_4) // Přidání všech migrací
                .fallbackToDestructiveMigration() // Zničí databázi při chybě migrace (volitelné, pokud ladíte)
                .build()
            INSTANCE = instance
            instance
        }
    }
}

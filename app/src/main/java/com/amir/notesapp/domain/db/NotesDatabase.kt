package com.amir.notesapp.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amir.notesapp.domain.db.dao.NotesDao
import com.amir.notesapp.domain.db.entity.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object {
        private val LOCK = Any()
        operator fun invoke(context: Context): NotesDatabase = synchronized(LOCK) {
            buildDatabase(context)
        }

        private fun buildDatabase(context: Context): NotesDatabase {
            return Room.databaseBuilder(context, NotesDatabase::class.java, "notes.db")
                .fallbackToDestructiveMigration().build()
        }
    }
}
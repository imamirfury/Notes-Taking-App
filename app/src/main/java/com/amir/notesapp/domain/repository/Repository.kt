package com.amir.notesapp.domain.repository

import androidx.lifecycle.LiveData
import com.amir.notesapp.domain.db.entity.Note

interface Repository {

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

    suspend fun update(note: Note)

    suspend fun allNotes(type: String): LiveData<List<Note>>
}
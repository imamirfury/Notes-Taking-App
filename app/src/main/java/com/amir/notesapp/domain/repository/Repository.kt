package com.amir.notesapp.domain.repository

import androidx.lifecycle.LiveData
import com.amir.notesapp.domain.db.entity.Note

/**
 * Created by Amir Fury on January 21 2022
 *
 * Email : fury.amir93@gmail.com
 *
 */

interface Repository {

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

    suspend fun update(note: Note)

    suspend fun allNotes(type: String): LiveData<List<Note>>
}
package com.amir.notesapp.domain.repository

import androidx.lifecycle.LiveData
import com.amir.notesapp.domain.db.dao.NotesDao
import com.amir.notesapp.domain.db.entity.Note
import com.amir.notesapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Amir Fury on January 21 2022
 *
 * Email : fury.amir93@gmail.com
 *
 */

class RepositoryImpl(private val dao: NotesDao) : Repository {
    override suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            dao.insert(note)
        }
    }

    override suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            dao.delete(note)
        }
    }

    override suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            dao.update(note)
        }
    }

    override suspend fun allNotes(type: String): LiveData<List<Note>> {
        return withContext(Dispatchers.IO) {
            if (type == Constants.all) {
                dao.allNotes()
            } else {
                dao.allNotes(type)
            }
        }
    }
}
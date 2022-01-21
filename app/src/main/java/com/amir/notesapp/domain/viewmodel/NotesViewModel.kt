package com.amir.notesapp.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.notesapp.domain.db.entity.Note
import com.amir.notesapp.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(private val repository: Repository) : ViewModel() {

    private val isImportant = MutableLiveData(false)

    fun isImportant(): Boolean = isImportant.value ?: false

    fun setIsImportant(value: Boolean) {
        isImportant.postValue(value)
    }

    fun insert(note: Note) {
        viewModelScope.launch { repository.insert(note) }
    }

    fun delete(note: Note) {
        viewModelScope.launch { repository.delete(note) }
    }

    fun update(note: Note) {
        viewModelScope.launch { repository.update(note) }
    }

    suspend fun allNotes(type: String): LiveData<List<Note>> {
        return withContext(Dispatchers.IO) {
            repository.allNotes(type)
        }
    }
}
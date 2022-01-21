package com.amir.notesapp.domain.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amir.notesapp.domain.db.entity.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM note_table WHERE noteType = :type")
    fun allNotes(type: String): LiveData<List<Note>>


    @Query("SELECT * FROM note_table")
    fun allNotes(): LiveData<List<Note>>
}
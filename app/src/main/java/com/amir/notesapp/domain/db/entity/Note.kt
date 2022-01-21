package com.amir.notesapp.domain.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amir.notesapp.utils.Constants

@Entity(tableName = "note_table")
data class Note(
    val noteText: String,
    var noteType: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun isImportant(): Boolean {
        return noteType != Constants.all
    }

    fun setImportant(value: Boolean) {
        noteType = if (value){
            Constants.important
        }else{
            Constants.all
        }
    }
}
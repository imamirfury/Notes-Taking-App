package com.amir.notesapp.listener

import com.amir.notesapp.domain.db.entity.Note

interface ItemClickListener {
    fun onItemClick(item: Note)
    fun onImportantClick(item: Note,important: Boolean)
}
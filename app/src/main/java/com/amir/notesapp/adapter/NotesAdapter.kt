package com.amir.notesapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.amir.notesapp.R
import com.amir.notesapp.databinding.ItemNotesBinding
import com.amir.notesapp.domain.db.entity.Note
import com.amir.notesapp.listener.ItemClickListener
import com.furybase.base.FuryBaseListAdapter

class NotesAdapter(private val listener: ItemClickListener) :
    FuryBaseListAdapter<Note>(notesDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_notes
    }

    companion object {
        val notesDiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id && oldItem.noteText == newItem.noteText
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id && oldItem.noteText == newItem.noteText
            }
        }
    }

    override fun onBindViewHolder(holder: FuryBaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.binding as ItemNotesBinding
        binding.switchButton.setOnCheckedChangeListener { compoundButton, b ->
            listener.onImportantClick(getItem(position), b)
        }
        holder.itemView.setOnClickListener { listener.onItemClick(getItem(position)) }
    }
}
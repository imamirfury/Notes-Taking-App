package com.amir.notesapp.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.amir.notesapp.R
import com.amir.notesapp.base.BaseActivity
import com.amir.notesapp.databinding.ActivityCreateNoteBinding
import com.amir.notesapp.domain.db.entity.Note
import com.amir.notesapp.domain.viewmodel.NotesViewModel
import com.amir.notesapp.utils.Constants
import com.furybase.extension.setErrorOnEditText
import com.furybase.extension.showSoftInput
import com.furybase.extension.string
import org.kodein.di.generic.instance

class CreateNoteActivity : BaseActivity<ActivityCreateNoteBinding>() {

    private val viewModel by instance<NotesViewModel>()

    override val layoutRes: Int get() = R.layout.activity_create_note

    override fun getToolbar(binding: ActivityCreateNoteBinding): Toolbar = binding.toolbar

    override fun onActivityReady(instanceState: Bundle?, binding: ActivityCreateNoteBinding) {
        enableBack()
        setToolbarTitle(string(R.string.newNote))
        binding.apply {
            input.requestFocus()
            input.showSoftInput()

            submitButton.setOnClickListener {
                val noteText = input.string()
                if (noteText.isEmpty()) {
                    input.setErrorOnEditText(string(R.string.noteCanNotBeEmpty))
                    return@setOnClickListener
                }
                val note = Note(
                    noteText,
                    if (viewModel.isImportant()) Constants.important else Constants.all
                )
                viewModel.insert(note)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_create_note, menu)
        val itemSwitch = menu.findItem(R.id.switchActionBar)
        itemSwitch.setActionView(R.layout.layout_switch)
        val switchButton: SwitchCompat =
            menu.findItem(R.id.switchActionBar).actionView.findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { _, b ->
            viewModel.setIsImportant(b)
        }
        return super.onCreateOptionsMenu(menu)
    }
}
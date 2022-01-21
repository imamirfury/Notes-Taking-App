package com.amir.notesapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.amir.notesapp.R
import com.amir.notesapp.adapter.NotesAdapter
import com.amir.notesapp.base.BaseActivity
import com.amir.notesapp.databinding.ActivityHomeBinding
import com.amir.notesapp.domain.db.entity.Note
import com.amir.notesapp.domain.viewmodel.NotesViewModel
import com.amir.notesapp.listener.ItemClickListener
import com.amir.notesapp.utils.applyItemDecoration
import com.amir.notesapp.utils.launchActivity
import com.furybase.extension.string
import com.furybase.extension.visibleListWithScale
import com.furybase.extension.visibleWithAnimation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class HomeActivity : BaseActivity<ActivityHomeBinding>(), ItemClickListener {


    private val viewModel by instance<NotesViewModel>()
    private val adapter by lazy { NotesAdapter(this) }

    override val layoutRes: Int
        get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar = binding.toolbar


    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        setToolbarTitle(string(R.string.app_name))
        binding.apply {
            addButton.visibleWithAnimation(700, 500)

            binding.addButton.setOnClickListener {
                launchActivity(
                    Intent(
                        this@HomeActivity,
                        CreateNoteActivity::class.java
                    )
                )
            }
            recycler.applyItemDecoration(5, 5, 5, 5)
            recycler.adapter = adapter
            recycler.visibleListWithScale(300, 300)
            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && addButton.visibility == View.VISIBLE) {
                        addButton.hide()
                    } else if (dy < 0 && addButton.visibility != View.VISIBLE) {
                        addButton.show()
                    }
                }
            })

        }
        fetchAllNotes(string(R.string.all))
    }

    private fun fetchAllNotes(type: String) = launch {
        val notes = viewModel.allNotes(type.lowercase())
        notes.observe(this@HomeActivity, {
            it?.let { list ->
                adapter.submitList(list.reversed())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            showFilterDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private var checkedItem = 0
    private fun showFilterDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(string(R.string.filterNotes))
            setNegativeButton(string(R.string.cancel)) { dialog, which ->

            }
            setPositiveButton(string(R.string.select)) { dialog, which ->
                val noteType = filters[checkedItem]
                fetchAllNotes(noteType)
            }
            setSingleChoiceItems(filters, checkedItem) { dialog, which ->
                checkedItem = which
            }
        }.show()
    }

    private val filters by lazy {
        arrayOf(
            string(R.string.all),
            string(R.string.important)
        )
    }

    override fun onItemClick(item: Note) {

    }

    override fun onImportantClick(item: Note, important: Boolean) {
        item.setImportant(important)
        viewModel.update(item)
    }
}
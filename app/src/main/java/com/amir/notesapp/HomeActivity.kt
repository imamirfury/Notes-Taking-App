package com.amir.notesapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.amir.notesapp.base.BaseActivity
import com.amir.notesapp.databinding.ActivityHomeBinding
import com.furybase.extension.string
import com.furybase.extension.toast
import com.furybase.extension.visibleListWithScale
import com.furybase.extension.visibleWithAnimation
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar = binding.toolbar


    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        setToolbarTitle(string(R.string.app_name))
        binding.apply {
            recycler.visibleListWithScale(300, 300)
            addButton.visibleWithAnimation(700, 500)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            filterDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private val filterDialog by lazy {
        var checkedItem = -1
        MaterialAlertDialogBuilder(this).apply {
            setTitle(string(R.string.filterNotes))
            setNegativeButton(string(R.string.cancel)) { dialog, which ->

            }
            setPositiveButton(string(R.string.select)) { dialog, which ->
                toast(filters[checkedItem])
            }
            setSingleChoiceItems(filters, checkedItem) { dialog, which ->
                checkedItem = which
            }
        }
    }

    private val filters by lazy {
        arrayOf(
            string(R.string.dateCreated),
            string(R.string.importent),
            string(R.string.priority)
        )
    }
}
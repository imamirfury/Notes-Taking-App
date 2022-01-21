package com.amir.notesapp.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.coroutines.CoroutineContext

/**
 * Created by Amir Fury on January 21 2022
 *
 * Email : fury.amir93@gmail.com
 *
 */

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), CoroutineScope,KodeinAware {

    override val kodein: Kodein by kodein()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private lateinit var job: Job

    protected val binding: B by lazy { DataBindingUtil.setContentView(this, layoutRes) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setSupportActionBar(getToolbar(binding))
        onActivityReady(savedInstanceState, binding)
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract fun getToolbar(binding: B): Toolbar?

    protected abstract fun onActivityReady(instanceState: Bundle?, binding: B)

    protected fun enableBack() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    protected fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setTitleDisable() = supportActionBar?.setDisplayShowTitleEnabled(false)


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun addTabLayoutMediator(
        tabLayout: TabLayout,
        viewPager2: ViewPager2,
        tabTitles: ArrayList<String>?,
        tabIcons: ArrayList<Drawable>?
    ) {
        TabLayoutMediator(tabLayout, viewPager2) { tab: TabLayout.Tab, _position: Int ->
            tabTitles?.let {
                for (i in it.indices) {
                    tab.text = it[_position]
                }
            }

            tabIcons?.let {
                for (i in it.indices) {
                    tab.icon = it[_position]
                }
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
package com.amir.notesapp.domain

import android.app.Application
import com.amir.notesapp.domain.db.NotesDatabase
import com.amir.notesapp.domain.db.dao.NotesDao
import com.amir.notesapp.domain.repository.Repository
import com.amir.notesapp.domain.repository.RepositoryImpl
import com.amir.notesapp.domain.viewmodel.NotesViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class NotesApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NotesApp))

        bind() from singleton { NotesDatabase.invoke(instance()) }
        bind<NotesDao>() with singleton { instance<NotesDatabase>().notesDao() }
        bind<Repository>() with singleton { RepositoryImpl(instance()) }
        bind() from singleton { NotesViewModel(instance()) }

    }
}
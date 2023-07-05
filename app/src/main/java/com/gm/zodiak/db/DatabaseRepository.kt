package com.gm.zodiak.db

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(application: Application) {

    private var zodiakDAO: ZodiakDAO
    private var allZodiak: LiveData<List<Zodiak>>

    private val database = ZodiakDB.getInstance(application)

    init {
        zodiakDAO = database.zodiaDAO()
        allZodiak = zodiakDAO.getAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(zodiak: Zodiak){
        zodiakDAO.insert(zodiak)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(zodiak: Zodiak){
        zodiakDAO.delete(zodiak)
    }

    fun getAllZodiak(): LiveData<List<Zodiak>> {
        return allZodiak
    }
}
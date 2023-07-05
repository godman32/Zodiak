package com.gm.zodiak

import android.app.Application
import com.gm.zodiak.db.DatabaseRepository
import com.gm.zodiak.db.Zodiak
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch

class MainVM(app: Application) : AndroidViewModel(app) {

    private val repository = DatabaseRepository(app)
    private val allZodiak = repository.getAllZodiak()


    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(zodiak: Zodiak) = viewModelScope.launch {
        repository.insert(zodiak)
    }

    fun delete(zodiak: Zodiak) = viewModelScope.launch {
        repository.delete(zodiak)
    }

    fun getAllZodiak(): LiveData<List<Zodiak>> {
        return allZodiak
    }

}
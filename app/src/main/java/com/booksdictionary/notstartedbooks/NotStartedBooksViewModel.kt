package com.booksdictionary.notstartedbooks

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.booksdictionary.database.BookDatabaseDao
import com.booksdictionary.database.BookInfo
import kotlinx.coroutines.*

class NotStartedBooksViewModel(
    val dao: BookDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var books = dao.getAll()

    suspend fun insert(book: BookInfo) {
        withContext(Dispatchers.IO) {
            dao.insert(book)
        }
    }

    init {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
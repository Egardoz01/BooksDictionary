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


    private suspend fun delete(book: BookInfo) {
        withContext(Dispatchers.IO) {
            dao.delete(book)
        }
    }

    fun deleteBook(book: BookInfo) {
        uiScope.launch {
            delete(book)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
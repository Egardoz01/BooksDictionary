package com.booksdictionary.notstartedbooks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.booksdictionary.database.BookDatabaseDao

class NotStartedBooksViewModelFactory (
    private val dao: BookDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotStartedBooksViewModel::class.java)) {
            return NotStartedBooksViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
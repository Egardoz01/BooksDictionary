package com.booksdictionary.book_dialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.booksdictionary.database.BookDatabaseDao
import com.booksdictionary.book_dialog.BookDialogViewModel

class BookDialogViewModelFactory (
    private val dao: BookDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDialogViewModel::class.java)) {
            return BookDialogViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
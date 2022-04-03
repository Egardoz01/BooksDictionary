package com.booksdictionary.book_dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.booksdictionary.database.BookDatabaseDao
import com.booksdictionary.database.BookInfo
import kotlinx.coroutines.*

class BookDialogViewModel (
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

    fun addBook(book: BookInfo)
    {
         uiScope.launch {
             insert(book)
         }
    }

    suspend fun update(book: BookInfo) {
        withContext(Dispatchers.IO) {
            dao.update(book)
        }
    }

    fun editBook(book: BookInfo)
    {
        uiScope.launch {
            update(book)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun validateBookInfo(bookInfo: BookInfo): Boolean {
        if (bookInfo.author.isEmpty())
            return false

        if (bookInfo.name.isEmpty())
            return false

        if (bookInfo.genre.isEmpty())
            return false

        if (bookInfo.status > 2 || bookInfo.status < 0)
            return false

        if (bookInfo.pagesRead < 0)
            return false

        if (bookInfo.pagesTotal < 0)
            return false

        if (bookInfo.pagesTotal < bookInfo.pagesRead)
            return false

        return true
    }


}
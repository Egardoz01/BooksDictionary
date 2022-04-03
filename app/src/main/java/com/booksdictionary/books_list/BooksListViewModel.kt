package com.booksdictionary.books_list

import android.app.Application
import androidx.lifecycle.*
import com.booksdictionary.database.BookDatabaseDao
import com.booksdictionary.database.BookInfo
import kotlinx.coroutines.*

class BooksListViewModel(
    val dao: BookDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var books: LiveData<List<BookInfo>>
    var filter = MutableLiveData<FilterData>(FilterData())

    init {
        books = Transformations.switchMap(filter) { filter ->
            filterBooks(filter.status, filter.author)
        }
    }

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

    private fun filterBooks(status: Int, author: String): LiveData<List<BookInfo>> {
        if (status in 0..2 && author.trim() == "") {
            return dao.getFilteredByStatus(status)
        } else if (status !in 0..2 && author.trim() != "") {
            return dao.getFilteredByAuthor(author)
        } else if (status in 0..2 && author.trim() != "") {
            return dao.getFilteredByAuthorAndStatus(author, status)
        }

        return dao.getAll()
    }

    class FilterData {
        var author: String = ""
        var status: Int = 3

    }
}
package com.booksdictionary.notstartedbooks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotStartedBooksViewModel : ViewModel() {

    var books = MutableLiveData<List<BookInfo>>(listOf(BookInfo("aaa","bbb")))

}
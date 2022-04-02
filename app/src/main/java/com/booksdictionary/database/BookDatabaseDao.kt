package com.booksdictionary.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface BookDatabaseDao {

    @Insert
    fun insert(book: BookInfo)

    @Update
    fun update(book: BookInfo)

    @Delete
    fun delete(book: BookInfo)

    @Query("SELECT * FROM book_table")
    fun getAll(): LiveData<List<BookInfo>>

    @Query("SELECT * FROM book_table WHERE status = :book_status")
    fun getFilteredByStatus(book_status: Int): LiveData<List<BookInfo>>

    @Query("SELECT * FROM book_table WHERE author LIKE '%'||:book_author||'%'")
    fun getFilteredByAuthor(book_author: String):LiveData<List<BookInfo>>

    @Query("SELECT * FROM book_table WHERE status = :book_status AND author LIKE '%'||:book_author||'%'")
    fun getFilteredByAuthorAndStatus(
        book_author: String,
        book_status: Int
    ): LiveData<List<BookInfo>>
}
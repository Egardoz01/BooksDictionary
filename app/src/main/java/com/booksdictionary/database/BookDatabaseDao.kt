package com.booksdictionary.database

import androidx.lifecycle.LiveData
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
}
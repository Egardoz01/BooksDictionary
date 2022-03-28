package com.booksdictionary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDatabaseDao {

    @Insert
    fun insert(book: BookInfo)

    @Update
    fun update(book: BookInfo)

    @Query("SELECT * FROM book_table")
    fun getAll(): LiveData<List<BookInfo>>
}
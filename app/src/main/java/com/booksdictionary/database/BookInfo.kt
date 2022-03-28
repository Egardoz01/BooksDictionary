package com.booksdictionary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
class BookInfo() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var bookId: Long = 0L

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "author")
    var author: String = ""
}
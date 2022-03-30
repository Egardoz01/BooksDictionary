package com.booksdictionary.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler


@Entity(tableName = "book_table")
@Parcelize
class BookInfo() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var bookId: Long = 0L

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "author")
    var author: String = ""

    constructor(parcel: Parcel) : this() {
        bookId = parcel.readLong()
        name = parcel.readString().toString()
        author = parcel.readString().toString()
    }

    companion object : Parceler<BookInfo> {

        override fun BookInfo.write(parcel: Parcel, flags: Int) {
            parcel.writeLong(bookId)
            parcel.writeString(name)
            parcel.writeString(author)
        }

        override fun create(parcel: Parcel): BookInfo {
            return BookInfo(parcel)
        }
    }
}
package com.booksdictionary.notstartedbooks

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booksdictionary.database.BookInfo

class BooksAdapter : RecyclerView.Adapter<BookItemViewHolder>() {
    var data = listOf<BookInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onEditBookClick: (BookInfo) -> Unit = fun(bookInfo: BookInfo) {}
        set(value) {
            field = value
        }


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val item = data[position]
        holder.bookName.text = item.name.toString()
        holder.bookAuthor.text = item.author.toString()
        holder.addButton.setOnClickListener {
            onEditBookClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder.from(parent)
    }
}
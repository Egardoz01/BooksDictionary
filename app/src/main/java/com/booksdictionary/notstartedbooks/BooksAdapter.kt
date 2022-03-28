package com.booksdictionary.notstartedbooks

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booksdictionary.database.BookInfo

class BooksAdapter: RecyclerView.Adapter<BookItemViewHolder>() {
    var data = listOf<BookInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val item = data[position]
        holder.bookName.text = item.name.toString()
        holder.bookAuthor.text = item.author.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder.from(parent)
    }
}
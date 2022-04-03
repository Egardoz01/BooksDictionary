package com.booksdictionary.notstartedbooks

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booksdictionary.R
import com.booksdictionary.database.BookInfo
import com.booksdictionary.database.StatusEnum

class BooksAdapter : RecyclerView.Adapter<BookItemViewHolder>() {
    var data = listOf<BookInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var onEditBookClick: (BookInfo) -> Unit
    lateinit var onDeleteBookClick: (BookInfo) -> Unit
    lateinit var context: Context

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val item = data[position]
        holder.bookName.text = item.name
        holder.bookAuthor.text = item.author
        holder.bookGenre.text = item.genre
        holder.bookPages.text =
            context.getString(R.string.pagesFormat).format(item.pagesRead, item.pagesTotal)
        holder.bookStatus.text = StatusEnum.values()[item.status].getLabel(context)
        if (item.pagesTotal != 0)
            holder.bookPersentage.text = (item.pagesRead * 100 / item.pagesTotal).toString() + "%";
        else
            holder.bookPersentage.text = "0%"

        if (item.imageURI != "") {
            holder.bookImage.setImageURI(Uri.parse((item.imageURI)))
        }

        holder.addButton.setOnClickListener {
            onEditBookClick(item)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteBookClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder.from(parent)
    }
}
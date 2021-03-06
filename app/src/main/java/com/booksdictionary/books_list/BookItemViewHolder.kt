package com.booksdictionary.books_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.booksdictionary.R

class BookItemViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    // val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    val bookName: TextView = itemView.findViewById(R.id.book_name)
    val bookAuthor: TextView = itemView.findViewById(R.id.book_author)
    val bookGenre: TextView = itemView.findViewById(R.id.book_genre)
    val bookPages: TextView = itemView.findViewById(R.id.book_pages)
    val bookPersentage: TextView = itemView.findViewById(R.id.book_persetage)
    val bookStatus: TextView = itemView.findViewById(R.id.book_status)
    val bookImage: ImageView = itemView.findViewById(R.id.book_image)

    val addButton: Button = itemView.findViewById(R.id.edit_button)
    val deleteButton: Button = itemView.findViewById(R.id.delete_button)

    companion object {
        fun from(parent: ViewGroup,): BookItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.book_item_view, parent, false)
            return BookItemViewHolder(view)
        }
    }

}
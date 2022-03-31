package com.booksdictionary.notstartedbooks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.booksdictionary.R
import com.booksdictionary.database.BookInfo

class BookItemViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    // val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    val bookName: TextView = itemView.findViewById(R.id.book_name)
    val bookAuthor: TextView = itemView.findViewById(R.id.book_author)
    val bookGenre: TextView = itemView.findViewById(R.id.book_genre)
    val bookPages: TextView = itemView.findViewById(R.id.book_pages)
    val bookPersentage: TextView = itemView.findViewById(R.id.book_persetage)
    val bookStatus: TextView = itemView.findViewById(R.id.book_status)

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
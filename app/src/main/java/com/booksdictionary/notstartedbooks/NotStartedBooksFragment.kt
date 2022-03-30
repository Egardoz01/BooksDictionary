package com.booksdictionary.notstartedbooks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.booksdictionary.R
import com.booksdictionary.database.BookDatabase
import com.booksdictionary.database.BookInfo
import com.booksdictionary.databinding.NotStartedBooksFragmentBinding

class NotStartedBooksFragment : Fragment() {

    companion object {
        fun newInstance() = NotStartedBooksFragment()
    }

    private lateinit var viewModel: NotStartedBooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: NotStartedBooksFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.not_started_books_fragment, container, false
        )

        var adapter = BooksAdapter()
        binding.booksList.adapter = adapter
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).getBookDatabaseDao()
        val viewModelFactory = NotStartedBooksViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NotStartedBooksViewModel::class.java)

        viewModel.books.observe(viewLifecycleOwner, Observer { books ->
            Log.i("observer: ", books.toString())
            if (books != null) {
                adapter.data = books
            }
        })

        binding.addBookButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_editBook)
        }
        adapter.onEditBookClick = {
            val action = NotStartedBooksFragmentDirections.actionEditBook()
            action.bookInfo = it
            this.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
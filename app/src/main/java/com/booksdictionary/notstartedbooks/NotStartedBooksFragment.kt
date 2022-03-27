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
import com.booksdictionary.R
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
        viewModel = ViewModelProvider(this).get(NotStartedBooksViewModel::class.java)

        viewModel.books.observe(viewLifecycleOwner, Observer { books ->
            Log.i("observer: ", books.toString())
            if (books != null) {

                adapter.data = books
                Log.i("adapter data: ", adapter.data.toString())
            }
        })

        viewModel.books.value = listOf(BookInfo("aaa","bbb"))

        Log.i("viewModel.books: ", viewModel.books.value.toString())
        Log.i("adapter data: ", adapter.data.toString())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
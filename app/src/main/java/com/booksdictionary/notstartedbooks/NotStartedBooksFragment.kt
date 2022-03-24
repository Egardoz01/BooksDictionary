package com.booksdictionary.notstartedbooks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booksdictionary.R

class NotStartedBooksFragment : Fragment() {

    companion object {
        fun newInstance() = NotStartedBooksFragment()
    }

    private lateinit var viewModel: NotStartedBooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.not_started_books_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotStartedBooksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
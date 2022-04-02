package com.booksdictionary.notstartedbooks

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.booksdictionary.MainActivity
import com.booksdictionary.R
import com.booksdictionary.database.BookDatabase
import com.booksdictionary.database.StatusEnumSelect
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

        (activity as MainActivity).supportActionBar?.title = getString(R.string.booksDictionary)


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
            if (books != null) {
                adapter.data = books
            }
        })

        binding.addBookButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_editBook)
        }

        binding.buttonFilter.setOnClickListener {


        }

        adapter.context = this.requireContext()

        adapter.onEditBookClick = {
            val action = NotStartedBooksFragmentDirections.actionEditBook()
            action.bookInfo = it
            this.findNavController().navigate(action)
        }

        adapter.onDeleteBookClick = {
            viewModel.deleteBook(it)
        }


        binding.spinnerStatus.adapter = SpinnerAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            StatusEnumSelect.values().map { it.getLabel(this.requireContext()) }
        )

        binding.spinnerStatus.setSelection(4)

        binding.buttonFilter.setOnClickListener {
            var filter = NotStartedBooksViewModel.FilterData()
            filter.status = binding.spinnerStatus.selectedItemPosition
            filter.author = binding.editAuthor.text.toString()

            viewModel.filter.postValue(filter)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}

class SpinnerAdapter(context: Context, resource: Int, list: List<String>) :
    ArrayAdapter<String>(context, resource, list) {

    override fun isEnabled(position: Int): Boolean {
        return position != 4
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: TextView = super.getDropDownView(position, convertView, parent) as TextView

        if (position == 4) {
            view.setTextColor(Color.GRAY)
        } else {

        }
        return view
    }

}
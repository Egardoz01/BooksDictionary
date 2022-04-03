package com.booksdictionary.books_list

import android.content.Context
import android.graphics.Color
import android.os.Bundle
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
import com.booksdictionary.databinding.BooksListFragmentBinding

class BooksListFragment : Fragment() {

    companion object {
        fun newInstance() = BooksListFragment()
    }

    private lateinit var viewModel: BooksListViewModel
    private lateinit var binding: BooksListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.books_list_fragment, container, false
        )
        initializeViewModel()
        initializeComponents()
        initializeRecycleViewAdapter()

        return binding.root
    }


    private fun initializeComponents() {

        binding.addBookButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_editBook)
        }

        binding.spinnerStatus.adapter = SpinnerAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            StatusEnumSelect.values().map { it.getLabel(this.requireContext()) }
        )

        binding.spinnerStatus.setSelection(4)

        binding.buttonFilter.setOnClickListener {
            var filter = BooksListViewModel.FilterData()
            filter.status = binding.spinnerStatus.selectedItemPosition
            filter.author = binding.editAuthor.text.toString()

            viewModel.filter.postValue(filter)
        }
    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).getBookDatabaseDao()
        val viewModelFactory = BooksListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BooksListViewModel::class.java)
    }

    private fun initializeRecycleViewAdapter() {
        var adapter = BooksAdapter()
        binding.booksList.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner, Observer { books ->
            if (books != null) {
                adapter.data = books
            }
        })

        adapter.context = this.requireContext()

        adapter.onEditBookClick = {
            val action = BooksListFragmentDirections.actionEditBook()
            action.bookInfo = it
            this.findNavController().navigate(action)
        }

        adapter.onDeleteBookClick = {
            viewModel.deleteBook(it)
        }

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
package com.booksdictionary.book_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.booksdictionary.R
import com.booksdictionary.database.BookDatabase
import com.booksdictionary.databinding.BookDialogFragmentBinding
import com.booksdictionary.book_dialog.BookDialogViewModel
import com.booksdictionary.book_dialog.BookDialogViewModelFactory
import com.booksdictionary.database.BookInfo

private lateinit var viewModel: BookDialogViewModel

class BookDialog : DialogFragment() {

    val args: BookDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).getBookDatabaseDao()
        val viewModelFactory = BookDialogViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BookDialogViewModel::class.java)


        val binding: BookDialogFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.book_dialog_fragment, container, false
        )
        binding.editAuthor.setText(args.bookInfo?.author ?: "")
        binding.editName.setText(args.bookInfo?.name ?: "")

        if (args.bookInfo == null) {
            binding.okButton.text = resources.getString(R.string.add)

            binding.okButton.setOnClickListener {
                var bookInfo = BookInfo()
                bookInfo.name = binding.editName.text.toString()
                bookInfo.author = binding.editAuthor.text.toString()

                viewModel.addBook(bookInfo)

                dismiss()
            }

        } else {
            binding.okButton.text = resources.getString(R.string.edit)

            binding.okButton.setOnClickListener {
                var bookInfo = BookInfo()
                bookInfo.bookId = args.bookInfo!!.bookId
                bookInfo.name = binding.editName.text.toString()
                bookInfo.author = binding.editAuthor.text.toString()

                viewModel.editBook(bookInfo)

                dismiss()
            }
        }


        return binding.root
    }
}
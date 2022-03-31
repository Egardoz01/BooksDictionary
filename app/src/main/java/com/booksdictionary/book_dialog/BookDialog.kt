package com.booksdictionary.book_dialog

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.booksdictionary.R
import com.booksdictionary.database.BookDatabase
import com.booksdictionary.database.BookInfo
import com.booksdictionary.databinding.BookDialogFragmentBinding


class BookDialog : DialogFragment() {

    private lateinit var viewModel: BookDialogViewModel

    private val args: BookDialogArgs by navArgs()

    private val authors: MutableList<EditText> = mutableListOf()

    private lateinit var binding: BookDialogFragmentBinding

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


        binding = DataBindingUtil.inflate(
            inflater, R.layout.book_dialog_fragment, container, false
        )


        authors.add(binding.editAuthor)

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

            var authorsList = args.bookInfo!!.author.split("; ")
            if (authorsList.size > 0) {
                binding.editAuthor.setText(authorsList[0])
                for (i in authorsList.indices) {
                    if (i > 0)
                        addAuthor(authorsList[i])
                }
            }


            binding.editName.setText(args.bookInfo!!.name ?: "")

            binding.okButton.text = resources.getString(R.string.edit)

            binding.okButton.setOnClickListener {
                var bookInfo = BookInfo()
                bookInfo.bookId = args.bookInfo!!.bookId
                bookInfo.name = binding.editName.text.toString()
                bookInfo.author = authors.map { it.text }.joinToString(separator = "; ")

                viewModel.editBook(bookInfo)

                dismiss()
            }
        }


        binding.buttonAddAuthor.setOnClickListener {
            addAuthor("")
        }

        return binding.root
    }

    private fun addAuthor(author: String) {
        var rl = binding.layout

        var edit = EditText(activity)
        edit.id = 200 + authors.size
        edit.layoutParams =
            ViewGroup.LayoutParams(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180f, resources.displayMetrics).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT);

        rl.addView(edit)
        edit.setText(author)

        val constraintLayout: ConstraintLayout = binding.layout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            edit.id,
            ConstraintSet.LEFT,
            authors.last().id,
            ConstraintSet.LEFT,
            0
        )
        constraintSet.connect(
            edit.id,
            ConstraintSet.TOP,
            authors.last().id,
            ConstraintSet.BOTTOM,
            20
        )
        constraintSet.applyTo(constraintLayout)


        val params = binding.editGenre.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = edit.id
        binding.editGenre.requestLayout()

        authors.add(edit)
    }

}
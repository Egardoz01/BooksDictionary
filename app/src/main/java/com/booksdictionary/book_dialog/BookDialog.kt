package com.booksdictionary.book_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.booksdictionary.R
import com.booksdictionary.databinding.BookDialogFragmentBinding

class BookDialog : DialogFragment() {

    val args: BookDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: BookDialogFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.book_dialog_fragment, container, false
        )


        binding.editAuthor.setText(args.bookInfo?.author ?: "")
        binding.editName.setText(args.bookInfo?.name ?: "")

        return binding.root
    }
}
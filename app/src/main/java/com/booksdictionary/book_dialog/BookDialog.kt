package com.booksdictionary.book_dialog


import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import com.booksdictionary.database.StatusEnum
import com.booksdictionary.databinding.BookDialogFragmentBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class BookDialog : DialogFragment() {

    private lateinit var viewModel: BookDialogViewModel

    private val args: BookDialogArgs by navArgs()

    private val authors: MutableList<EditText> = mutableListOf()

    private lateinit var binding: BookDialogFragmentBinding

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeViewModel()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.book_dialog_fragment, container, false
        )

        initializeComponents()

        return binding.root
    }

    private fun getBookInfo(): BookInfo {
        var bookInfo = BookInfo()

        bookInfo.name = binding.editName.text.toString()
        bookInfo.author = authors.map { it.text }.joinToString(separator = "; ")
        bookInfo.genre = binding.editGenre.text.toString()

        bookInfo.pagesTotal = binding.editPagesTotal.text.toString().toInt()
        bookInfo.pagesRead = binding.editPagesRead.text.toString().toInt()
        bookInfo.status = binding.spinnerStatus.selectedItemPosition
        bookInfo.imageURI = imageUri.toString()

        return bookInfo
    }

    private fun showMessage(message: String) {

        val builder = AlertDialog.Builder(this.requireContext())

        with(builder)
        {
            setTitle("Invalid Input")
            setMessage(message)
            show()
        }


    }

    private fun addAuthor(author: String) {
        var rl = binding.layout

        var edit = EditText(activity)
        edit.id = 200 + authors.size
        edit.layoutParams =
            ViewGroup.LayoutParams(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    180f,
                    resources.displayMetrics
                ).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT
            );

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

    private fun startGallery() {
        val gallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            var uri = data?.data
            imageUri = uri?.let { saveImageToInternalStorage(it) }
            binding.imageView.setImageURI(imageUri)
        }
    }

    private fun saveImageToInternalStorage(imageUri: Uri): Uri {

        val bitmap =
            MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)

        val wrapper = ContextWrapper(this.requireContext())

        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            stream.flush()

            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).getBookDatabaseDao()
        val viewModelFactory = BookDialogViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BookDialogViewModel::class.java)

    }

    private fun initializeAddBook() {
        binding.okButton.text = resources.getString(R.string.add)

        binding.okButton.setOnClickListener {
            try {
                var bookInfo = getBookInfo()
                if (viewModel.validateBookInfo(bookInfo)) {
                    viewModel.addBook(bookInfo)
                    dismiss()
                } else
                    showMessage("Invalid Input")
            } catch (ex: Exception) {
                showMessage("Invalid Input")
            }
        }

        args.bookInfo?.let { binding.spinnerStatus.setSelection(0) }
    }

    private fun initializeEditBook()
    {
        var authorsList = args.bookInfo!!.author.split("; ")
        if (authorsList.isNotEmpty()) {
            binding.editAuthor.setText(authorsList[0])
            for (i in authorsList.indices) {
                if (i > 0)
                    addAuthor(authorsList[i])
            }
        }

        binding.editName.setText(args.bookInfo!!.name ?: "")
        binding.editGenre.setText(args.bookInfo!!.genre ?: "")
        binding.editPagesTotal.setText(args.bookInfo!!.pagesTotal.toString() ?: "")
        binding.editPagesRead.setText(args.bookInfo!!.pagesRead.toString() ?: "")
        imageUri = Uri.parse(args.bookInfo!!.imageURI)
        binding.imageView.setImageURI(imageUri)

        args.bookInfo?.let { binding.spinnerStatus.setSelection(it.status) }
        binding.okButton.text = resources.getString(R.string.edit)

        binding.okButton.setOnClickListener {
            try {

                var book = getBookInfo()
                if (viewModel.validateBookInfo(book)) {
                    book.bookId = args.bookInfo!!.bookId
                    viewModel.editBook(book)
                    dismiss()
                } else
                    showMessage("Invalid Input")
            } catch (ex: Exception) {

                showMessage("Invalid Input")
            }
        }
    }

    private fun initializeComponents() {
        authors.add(binding.editAuthor)

        if (args.bookInfo == null) {
            initializeAddBook()
        } else {
            initializeEditBook()
        }

        binding.buttonUploadImage.setOnClickListener {
            startGallery()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonAddAuthor.setOnClickListener {
            addAuthor("")
        }

        binding.spinnerStatus.adapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            StatusEnum.values().map { it.getLabel(this.requireContext()) }
        )


    }

}
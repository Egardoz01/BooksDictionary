package com.booksdictionary.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.booksdictionary.MainActivity
import com.booksdictionary.R
import com.booksdictionary.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )


        binding.btnNotStartedBooks.setOnClickListener {
            this.findNavController().navigate(R.id.action_menuFragment_to_BooksListFragment)
        }

        binding.btnExit.setOnClickListener {
            activity?.let { it1 -> finishAffinity(it1) }
        }

        (activity as MainActivity).supportActionBar?.title = getString(R.string.booksDictionary)

        return binding.root

    }


}
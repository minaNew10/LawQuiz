package com.example.lawquiz.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lawquiz.R
import com.example.lawquiz.utils.setAlign


class MainCategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = MainCategoriesFragment()
    }

    private lateinit var viewModel: MainCategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainCategoriesViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        var actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.setAlign(activity as AppCompatActivity,getString(R.string.main_questions_categories_title))
    }

}
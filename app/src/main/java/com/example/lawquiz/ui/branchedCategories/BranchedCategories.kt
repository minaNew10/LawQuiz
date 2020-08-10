package com.example.lawquiz.ui.branchedCategories

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lawquiz.R

class BranchedCategories : Fragment() {

    companion object {
        fun newInstance() = BranchedCategories()
    }

    private lateinit var viewModel: BranchedCategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.branched_categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BranchedCategoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
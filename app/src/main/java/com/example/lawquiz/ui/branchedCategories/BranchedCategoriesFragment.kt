package com.example.lawquiz.ui.branchedCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lawquiz.R
import com.example.lawquiz.databinding.BranchedCategoriesFragmentBinding
import com.example.lawquiz.ui.categories.BranchClickListener
import com.example.lawquiz.ui.categories.CategoriesAdapter
import com.example.lawquiz.utils.setAlign

    private const val TAG = "BranchedCategories"
class BranchedCategoriesFragment : Fragment() {

    private lateinit var cat : String

    private var _binding: BranchedCategoriesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BranchedCategoriesViewModel
    private lateinit var viewModelFactory: BranchedCategoriesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cat = BranchedCategoriesFragmentArgs.fromBundle(requireArguments()).mainCategroy
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.branched_categories_fragment,container,false)
        viewModelFactory = BranchedCategoriesViewModelFactory(cat)
        viewModel = ViewModelProvider(this,viewModelFactory).get(BranchedCategoriesViewModel::class.java)
        val adapter = CategoriesAdapter(BranchClickListener { name ->
            viewModel.onBranchedCategoryClicked(name)
        })
        binding.branchedCategoriesList.adapter = adapter
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })
        return binding.root
    }



    override fun onResume() {
        super.onResume()
        var actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.setAlign(activity as AppCompatActivity,getString(R.string.branched_categories_title))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
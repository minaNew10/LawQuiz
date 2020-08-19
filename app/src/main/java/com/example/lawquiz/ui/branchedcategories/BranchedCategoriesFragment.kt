package com.example.lawquiz.ui.branchedcategories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.lawquiz.R
import com.example.lawquiz.databinding.BranchedCategoriesFragmentBinding
import com.example.lawquiz.model.Category
import com.example.lawquiz.utils.setAlign

    private const val TAG = "BranchedCategories"
class BranchedCategoriesFragment : Fragment() {

    private lateinit var category : Category

    private var _binding: BranchedCategoriesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BranchedCategoriesViewModel
    private lateinit var viewModelFactory: BranchedCategoriesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        category = BranchedCategoriesFragmentArgs.fromBundle(requireArguments()).mainCategroy
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.branched_categories_fragment,container,false)
        viewModelFactory = BranchedCategoriesViewModelFactory(category)
        viewModel = ViewModelProvider(this,viewModelFactory).get(BranchedCategoriesViewModel::class.java)
        val adapter = BranchedCategoriesAdapter(BranchedCategoriesClickListener { cat ->
            viewModel.onBranchedCategoryClicked(cat)
        })
        binding.branchedCategoriesList.adapter = adapter
        setupViewModel(adapter)
        return binding.root
    }

    private fun setupViewModel(adapter: BranchedCategoriesAdapter) {
        viewModel.currCategory.observe(viewLifecycleOwner, Observer {
            adapter.data = it.branches!!
        })

        viewModel.navigateToTest.observe(viewLifecycleOwner, Observer {

            it?.let {
                var questionClass = it.parent.plus("_").plus(it.name.replace(" ", "_"))
                val action =
                    BranchedCategoriesFragmentDirections.actionBranchedCategoriesToQuestionFragmet(
                        questionClass
                    )
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.onTestNavigated()
    //                 Toast.makeText(activity,it.parent +"_"+ it.name.replace(" ","_"),Toast.LENGTH_LONG).show()
            }
        })
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
package com.example.lawquiz.ui.categories

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.lawquiz.R
import com.example.lawquiz.databinding.MainCategoriesFragmentBinding
import com.example.lawquiz.utils.setAlign


class MainCategoriesFragment : Fragment() {



    private var _binding: MainCategoriesFragmentBinding? = null
    private val binding get() = _binding!!

//    private val viewModel: MainCategoriesViewModel  by lazy {
//        ViewModelProvider(this).get(MainCategoriesViewModel::class.java)
//    }
    private lateinit var viewModel:MainCategoriesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding : MainCategoriesFragmentBinding = DataBindingUtil.inflate(inflater,
//                    R.layout.main_categories_fragment,container,false)
        _binding = DataBindingUtil.inflate(inflater,
                    R.layout.main_categories_fragment,container,false)
        viewModel = ViewModelProvider(this).get(MainCategoriesViewModel::class.java)
        val adapter = CategoriesAdapter(MainCategoryClickListener { name ->
            viewModel.onMainCategoryClicked(name)
        })
        binding.mainCategoriesList.adapter = adapter
        setupViewModel(adapter)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupViewModel(adapter: CategoriesAdapter) {
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })
        viewModel.navigateToBranchedCategories.observe(viewLifecycleOwner, Observer { categories ->
            categories?.let {
                //to navigate to branches only if exists otherwise navigate to the test directly
                if (it.branches!!.size > 1) {
                    val action =
                        MainCategoriesFragmentDirections.actionMainCategoriesFragmentToBranchedCategories(
                            it
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.onBranchedCategoriesNavigated()
                } else {
                    Toast.makeText(activity, "no branches", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        var actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.setAlign(activity as AppCompatActivity,getString(R.string.main_questions_categories_title))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_main_categories,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sign_out ->  {
                    viewModel.signOut()
                    findNavController().navigate(R.id.action_mainCategoriesFragment_to_loginFragment)
                    return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }

        }

    }
}
package com.example.lawquiz.ui.categories

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_main_categories,menu)

        super.onCreateOptionsMenu(menu, inflater)
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
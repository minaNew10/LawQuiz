package com.example.lawquiz.ui.questions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lawquiz.R
import com.example.lawquiz.databinding.MainCategoriesFragmentBinding
import com.example.lawquiz.databinding.QuestionFragmentBinding

class QuestionFragmet : Fragment() {
    private lateinit var questionClass : String
    private var _binding: QuestionFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: QuestionFragmetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,
                    R.layout.question_fragment,container,false)
        viewModel= ViewModelProvider(this).get(QuestionFragmetViewModel::class.java)
        questionClass = QuestionFragmetArgs.fromBundle(requireArguments()).questionClassification
        Toast.makeText(activity,questionClass,Toast.LENGTH_LONG).show()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionFragmetViewModel::class.java)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.lawquiz.ui.questions


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lawquiz.R
import com.example.lawquiz.databinding.QuestionFragmentBinding


class QuestionFragment : Fragment() {
    private lateinit var questionClass : String
    private var _binding: QuestionFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: QuestionFragmetViewModel
    private lateinit var viewModelFactory: QuestionViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,
                    R.layout.question_fragment,container,false)
        questionClass = QuestionFragmentArgs.fromBundle(requireArguments()).questionClassification
        viewModelFactory = QuestionViewModelFactory(questionClass)
        viewModel= ViewModelProvider(this,viewModelFactory).get(QuestionFragmetViewModel::class.java)
        viewModel.currCategory.observe(viewLifecycleOwner, Observer {
             Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
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
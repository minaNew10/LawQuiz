package com.example.lawquiz.ui.questions


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.question_fragment.*

private const val TAG = "QuestionFragment"
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
        _binding?.questionViewModel = viewModel
        _binding?.lifecycleOwner = viewLifecycleOwner
        viewModel.currQuestion.observe(viewLifecycleOwner, Observer {
            //in case of correct answer
            if (it.chosenAnswer != -1 && it.chosenAnswer == it.correctAnswer.toInt()) {
                Log.i(TAG, "onCreateView: answer is correct")
                higlightCorrectAnswer(it.chosenAnswer)
            }
        }
        )

        return binding.root
    }

    fun higlightCorrectAnswer(ans: Int){
        when(ans){
            1 -> binding.txtvOptionOne.background = activity?.getDrawable(R.drawable.correct_option_border_bg)
            2 -> binding.txtvOptionTwo.background = activity?.getDrawable(R.drawable.correct_option_border_bg)
            3 -> binding.txtvOptionThree.background = activity?.getDrawable(R.drawable.correct_option_border_bg)
            4 -> binding.txtvOptionFour.background = activity?.getDrawable(R.drawable.correct_option_border_bg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
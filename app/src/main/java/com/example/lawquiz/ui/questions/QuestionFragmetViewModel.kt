package com.example.lawquiz.ui.questions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.blogc.android.views.ExpandableTextView
import com.example.lawquiz.R
import com.example.lawquiz.model.Question
import com.example.lawquiz.model.Test
import com.example.lawquiz.utils.mutation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "QuestionViewModel"
class QuestionFragmetViewModel(questionClass: String) : ViewModel() {
    //the number of questions in the test
    private val _numOfQuestions = MutableLiveData<Int>()
        val numOfQuestions: LiveData<Int>
            get() = _numOfQuestions

    //the current position of the question
    private val _currPosition = MutableLiveData<Int>()
        val currPosition: LiveData<Int>
            get() = _currPosition

    //the current question
    private val _currQuestion = MutableLiveData<Question>()
        val currQuestion: LiveData<Question>
            get() = _currQuestion
    //list of questions for the test
    private val _questionsLiveData = MutableLiveData<ArrayList<Question?>>()
        val questionLiveData: LiveData<ArrayList<Question?>>
            get() = _questionsLiveData

    //the category of the test
    private val _currCategory = MutableLiveData<String>()
    val currCategory: LiveData<String>
        get() = _currCategory
    private var database: DatabaseReference
    //background of the first textview
    private val _firstChoiceBg = MutableLiveData<Int>()
        val firstChoiceBg: LiveData<Int>
            get() = _firstChoiceBg
    private val _secChoiceBg = MutableLiveData<Int>()
        val secChoiceBg: LiveData<Int>
            get() = _secChoiceBg
    private val _thirdChoiceBg = MutableLiveData<Int>()
        val thirdChoiceBg: LiveData<Int>
            get() = _thirdChoiceBg
    private val _forthChoiceBg = MutableLiveData<Int>()
        val forthChoiceBg: LiveData<Int>
            get() = _forthChoiceBg

    private val _btnSubmitText = MutableLiveData<Int>()
        val btnSubmitText: LiveData<Int>
            get() = _btnSubmitText

  //this is the chosen answer from the view
   private val _chosenAnswer = MutableLiveData<Int?>()
       val chosenAnswer: LiveData<Int?>
           get() = _chosenAnswer


    init {
        //if the question is answered before, chosenAnswered will be assigned the chosen answer number otherwise it will be -1
        _chosenAnswer.value = _currQuestion.value?.chosenAnswer
        _currCategory.value = questionClass
        database = Firebase.database.reference
        var questions = ArrayList<Question?>()
        val questionsListener = object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

             }

            override fun onDataChange(snapshot: DataSnapshot) {
                var numOfChildrens = snapshot.children.count()
                var list = snapshot.children?.take(if (numOfChildrens >= 10) 10 else numOfChildrens )

                _numOfQuestions.value = list.size
                list.forEach{
                    var  question : Question? =  it.getValue(Question::class.java)
                    question?.id = it.key
                    questions.add(question)
                }
                _questionsLiveData.value = questions
                _currPosition.value = 0
               _currQuestion.value =  _currPosition.value?.let {
                    _questionsLiveData.value?.get(it)
                }
            }

        }
        database.child("questions/$questionClass/moderate").addValueEventListener(questionsListener)
        _btnSubmitText.value = R.string.submit_answer
        useDefaultBgAndTexts()
    }
    fun useDefaultBgAndTexts(){
        _firstChoiceBg.value = R.drawable.default_option_border_bg
        _secChoiceBg.value = R.drawable.default_option_border_bg
        _thirdChoiceBg.value = R.drawable.default_option_border_bg
        _forthChoiceBg.value = R.drawable.default_option_border_bg

//       _btnSubmitText.value = R.string.submit_answer
    }
    fun createTest(questions : ArrayList<Question?>) : LiveData<Test>{
        var testLiveData = MutableLiveData<Test>()
        var test = Test("1",10,"moderate",currCategory.value,questions,0);
        testLiveData.value = test
        return testLiveData
    }
    fun getCurrQuestionLiveData() : LiveData<Question>{
        val pos  : Int? = _currPosition.value
        _currQuestion.value = pos?.let { _questionsLiveData.value?.get(it) }
        return currQuestion
    }
    fun nextQuestion(){
        val pos = _currPosition.value
        val nums = _numOfQuestions.value
        if(pos!!.compareTo(nums!! - 1) < 0) {
            _currPosition.value = (currPosition.value)?.plus(1)
            _currQuestion.value = currPosition.value?.let {
                _questionsLiveData.value?.get(it)
            }

        }
        //regain the default background for photos
        useDefaultBgAndTexts()
        _btnSubmitText.value = R.string.submit_answer
    }
    fun prevQuestion(){
        _currPosition.value = (currPosition.value)?.minus(1)
        _currQuestion.value = currPosition.value?.let{
            _questionsLiveData.value?.get(it)
        }
    }

    fun submitAnswer(){
        //only if chosen answer = -1 which means the question is not answered before
        if(_currQuestion?.value?.chosenAnswer == -1) {

            var lastChosenAns = _chosenAnswer.value

            _currQuestion.mutation {
                it.value!!.chosenAnswer = lastChosenAns!!
            }
            var correctAns = _currQuestion.value?.correctAnswer?.toInt()
            if (lastChosenAns != correctAns) {
                Log.i(TAG, "submitAnswer: correctAns is " + _currQuestion.value!!.correctAnswer)
            }else{
                Log.i(TAG, "submitAnswer: answer is correct")
            }
            _btnSubmitText.value = R.string.next_Question
        }else{
            nextQuestion()

        }

    }
    fun onFirstChoiceClicked(){
        //only if the question is not answered before change the chosen answer
        if(_currQuestion?.value?.chosenAnswer == -1) {
            _chosenAnswer.value = 1
            _firstChoiceBg.value = R.drawable.selected_option_border_bg
            _secChoiceBg.value = R.drawable.default_option_border_bg
            _thirdChoiceBg.value = R.drawable.default_option_border_bg
            _forthChoiceBg.value = R.drawable.default_option_border_bg
        }
    }
    fun onSecondChoiceClicked(){
        //only if the question is not answered before change the chosen answer
        if(_currQuestion?.value?.chosenAnswer == -1) {
            _chosenAnswer.value = 2
            _secChoiceBg.value = R.drawable.selected_option_border_bg
            _firstChoiceBg.value = R.drawable.default_option_border_bg
            _thirdChoiceBg.value = R.drawable.default_option_border_bg
            _forthChoiceBg.value = R.drawable.default_option_border_bg
        }
    }
    fun onThirdChoiceClicked(){
        //only if the question is not answered before change the chosen answer
        if(_currQuestion?.value?.chosenAnswer == -1) {
            _chosenAnswer.value = 3
            _thirdChoiceBg.value = R.drawable.selected_option_border_bg
            _firstChoiceBg.value = R.drawable.default_option_border_bg
            _secChoiceBg.value = R.drawable.default_option_border_bg
            _forthChoiceBg.value = R.drawable.default_option_border_bg
        }
    }
    fun onForthChoiceClicked(){
        //only if the question is not answered before change the chosen answer
        if(_currQuestion?.value?.chosenAnswer == -1) {
            _chosenAnswer.value = 4
            _forthChoiceBg.value = R.drawable.selected_option_border_bg
            _firstChoiceBg.value = R.drawable.default_option_border_bg
            _secChoiceBg.value = R.drawable.default_option_border_bg
            _thirdChoiceBg.value = R.drawable.default_option_border_bg
        }
    }





}
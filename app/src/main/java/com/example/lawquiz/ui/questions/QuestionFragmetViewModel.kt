package com.example.lawquiz.ui.questions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lawquiz.model.Question
import com.example.lawquiz.model.Test
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "QuestionViewModel"
class QuestionFragmetViewModel(questionClass: String) : ViewModel() {
    private val _questionsLiveData = MutableLiveData<ArrayList<Question?>>()
        val questionLiveData: LiveData<ArrayList<Question?>>
            get() = _questionsLiveData
    private val _currCategory = MutableLiveData<String>()
    val currCategory: LiveData<String>
        get() = _currCategory
    private var database: DatabaseReference
    init {
        _currCategory.value = questionClass
        database = Firebase.database.reference
        var questions = ArrayList<Question?>()
        val questionsListener = object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var list = snapshot.children.take(10)
                list.forEach{
                    var  question : Question? =  it.getValue(Question::class.java)
                    question?.id = it.key
                    questions.add(question)
                }
                _questionsLiveData.value = questions
            }

        }
        database.child("questions/$questionClass/moderate").addValueEventListener(questionsListener)
    }

    public fun createTest(questions : ArrayList<Question?>) : LiveData<Test>{
        var testLiveData = MutableLiveData<Test>()
        var test = Test("1",10,"moderate",currCategory.value,questions,0);
        testLiveData.value = test
        return testLiveData
    }
}
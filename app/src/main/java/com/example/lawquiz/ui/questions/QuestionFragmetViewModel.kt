package com.example.lawquiz.ui.questions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lawquiz.model.Category

private const val TAG = "QuestionViewModel"
class QuestionFragmetViewModel(questionClass: String) : ViewModel() {
    private val _currCategory = MutableLiveData<String>()
    val currCategory: LiveData<String>
        get() = _currCategory
    init {
        _currCategory.value = questionClass
    }
}
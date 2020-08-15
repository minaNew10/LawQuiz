package com.example.lawquiz.ui.branchedCategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lawquiz.model.Category
import com.google.firebase.database.DatabaseReference

private const val TAG = "BranchedCategoriesView"
class BranchedCategoriesViewModel(category: Category) : ViewModel() {




    private val _currCategory = MutableLiveData<Category>()
    val currCategory: LiveData<Category>
        get() = _currCategory

    private lateinit var database: DatabaseReference
    init {
        _currCategory.value = category
    }
    fun onBranchedCategoryClicked(category: Category) {

    }
}
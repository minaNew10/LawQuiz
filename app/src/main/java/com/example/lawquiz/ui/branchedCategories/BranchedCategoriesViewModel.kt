package com.example.lawquiz.ui.branchedCategories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "BranchedCategoriesView"
class BranchedCategoriesViewModel(categroy: String) : ViewModel() {


    var mainCategory = categroy

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>>
        get() = _categories

    private lateinit var database: DatabaseReference
    init {
        Log.i(TAG, "main category is " + mainCategory)
        database = Firebase.database.reference
        var list = mutableListOf<String>()
        val categoryListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    if(it.hasChildren()) {
                        list.add(it.key.toString())
                        Log.i(TAG, "onDataChange: ${it.key.toString()}")
                    }

                }
                _categories.value = list
            }


        }
        database.child("categories/$categroy").addValueEventListener(categoryListener)
    }
    fun onBranchedCategoryClicked(name: String) {

    }
}
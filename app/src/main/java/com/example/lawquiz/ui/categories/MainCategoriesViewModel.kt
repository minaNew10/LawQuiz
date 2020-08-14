package com.example.lawquiz.ui.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lawquiz.model.Category
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.example.lawquiz.ui.login.LoginStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

private const val TAG = "MainCategoriesViewModel"
class MainCategoriesViewModel : ViewModel() {

    private val _navigateToBranchedCategories = MutableLiveData<String>()
        val navigateToBranchedCategories: LiveData<String>
            get() = _navigateToBranchedCategories

    private val _categories = MutableLiveData<List<String>>()
        val categories: LiveData<List<String>>
            get() = _categories

    private var mAuth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private val _loggedInUser = MutableLiveData<FirebaseUser>()
    val loggedInUser: LiveData<FirebaseUser>
        get() = _loggedInUser

    init {
        mAuth = FirebaseAuth.getInstance();
        _loggedInUser.value = mAuth?.currentUser
        database = Firebase.database.reference

        var list = mutableListOf<String>()
        val categoryListener = object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
               snapshot.children.forEach{
                   category -> list.add(category.key.toString())

//                   categories.add(category.key.toString())
//                   Log.i(TAG, "onDataChange: " + categories[0])
               }
                _categories.value = list
            }


        }
        database.child("categories").addValueEventListener(categoryListener)

    }

    fun signOut(){
        mAuth?.signOut()
    }
    fun onMainCategoryClicked(name: String){
        _navigateToBranchedCategories.value  = name
    }

    fun onBranchedCategoriesNavigated(){
        _navigateToBranchedCategories.value = null
    }
}
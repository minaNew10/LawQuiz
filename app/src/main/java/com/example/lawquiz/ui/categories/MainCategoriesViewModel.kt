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

    private val _navigateToBranchedCategories = MutableLiveData<Category>()
        val navigateToBranchedCategories: LiveData<Category>
            get() = _navigateToBranchedCategories

    private val _categories = MutableLiveData<ArrayList<Category>>()
        val categories: LiveData<ArrayList<Category>>
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

        var list = ArrayList<Category>()


        val categoryListener = object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
//                var cat : Category = snapshot.key.let { Category(it) }
               snapshot.children.forEach{
                   var cat =  Category(it.key.toString())
                   var childrenList = ArrayList<Category>()
                   it.children.forEach {
                           branch -> if(branch.hasChildren())
                                     childrenList?.add(Category(name = branch.key.toString(),parent = it.key.toString()))
                   }
                   cat.branches = childrenList
                   list?.add(cat)
                   
               }
                _categories.value = list
            }


        }
        database.child("categories").addValueEventListener(categoryListener)

    }

    fun signOut(){
        mAuth?.signOut()
    }
    fun onMainCategoryClicked(cat: Category){
        _navigateToBranchedCategories.value  = cat
    }
    fun onBranchedCategoriesNavigated(){
        _navigateToBranchedCategories.value = null
    }
}
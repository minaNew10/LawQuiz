package com.example.lawquiz.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lawquiz.ui.login.LoginStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainCategoriesViewModel : ViewModel() {
    private var mAuth: FirebaseAuth? = null
    private val _loggedInUser = MutableLiveData<FirebaseUser>()
    val loggedInUser: LiveData<FirebaseUser>
        get() = _loggedInUser

    init {
        mAuth = FirebaseAuth.getInstance();
        _loggedInUser.value = mAuth?.currentUser

    }

    fun signOut(){
        mAuth?.signOut()

    }
}
package com.example.lawquiz.ui.login

import android.R.attr
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginViewModel: ViewModel(){
    private var mAuth: FirebaseAuth? = null


    private val _loggedInUser = MutableLiveData<FirebaseUser>()
    val loggedInUser: LiveData<FirebaseUser>
            get() = _loggedInUser
    private val _authResult = MutableLiveData<Task<AuthResult?>>()
    val authResult: LiveData<Task<AuthResult?>>
        get() = _authResult

    init {
        mAuth = FirebaseAuth.getInstance();
        _loggedInUser.value = mAuth?.currentUser
    }

    fun signIn(email :String, password: String){
       mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener{
           _authResult.value = it
        }

    }
    fun signUp(email: String, password :String){
      mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener{
          _authResult.value = it
      }
    }
    fun signOut(){
        mAuth?.signOut()

    }

}
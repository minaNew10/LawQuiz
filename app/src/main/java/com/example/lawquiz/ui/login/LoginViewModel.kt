package com.example.lawquiz.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lawquiz.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.IOException

enum class LoginStatus {LOADING,DONE,IDLE}

class LoginViewModel: ViewModel(){
    private var mAuth: FirebaseAuth? = null

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm


    private val _loggedInUser = MutableLiveData<FirebaseUser>()
    val loggedInUser: LiveData<FirebaseUser>
            get() = _loggedInUser

    private val _authResult = MutableLiveData<Task<AuthResult?>>()
    val authResult: LiveData<Task<AuthResult?>>
        get() = _authResult


    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<LoginStatus>()

    // The external immutable LiveData for the response String
    val status: LiveData<LoginStatus>
        get() = _status
    init {
        mAuth = FirebaseAuth.getInstance();
        _loggedInUser.value = mAuth?.currentUser
        _status.value = LoginStatus.IDLE
    }

    fun signIn(email :String, password: String){
        _status.value = LoginStatus.LOADING
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            _authResult.value = it
            _status.value = LoginStatus.DONE
        }
    }

    fun signUp(email: String, password :String){
        _status.value = LoginStatus.LOADING
      mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener{
          _authResult.value = it
          _status.value = LoginStatus.DONE
      }
    }

    fun signOut(){
        mAuth?.signOut()

    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        return if (!email.isEmpty()) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}
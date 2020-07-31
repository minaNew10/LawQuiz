package com.example.lawquiz

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lawquiz.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


private const val TAG = "LoginFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding : FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        setupViewModel()
        binding.btnLogin.isEnabled = true
        binding.txtvRegister.isEnabled = true
        binding.btnLogin.setOnClickListener{
            signIn(binding.etxtEmailLogin.text.toString(),binding.etxtPsswrdLogin.text.toString())
        }
        binding.txtvRegister.setOnClickListener{
            signUp(binding.etxtEmailLogin.text.toString(),binding.etxtPsswrdLogin.text.toString())
        }
        binding.txtvAppName.setOnClickListener{
            signOut()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupViewModel() {
        loginViewModel.loggedInUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,"you r already LoggedIn",Toast.LENGTH_LONG).show()
            } ?: Toast.makeText(activity,"session ended",Toast.LENGTH_LONG).show()
        })
        loginViewModel.authResult.observe(viewLifecycleOwner, Observer {

            if(it != null && it.isComplete){

                if(it.isSuccessful){
                    Toast.makeText(activity,"دخول ناجح",Toast.LENGTH_LONG).show()
                }else{
                    
                    Toast.makeText(activity,it.exception?.message ?: "",Toast.LENGTH_LONG).show()
                }
            }else{
                Log.i(TAG, "setupViewModel: not coml" )
            }
        })
    }

    private fun signIn(email: String,psswrd :String){
        loginViewModel.signIn(email,psswrd)
    }
    private fun signUp(email: String,psswrd :String){
        loginViewModel.signUp(email,psswrd)
    }
    private fun signOut(){
        loginViewModel.signOut()
    }

}
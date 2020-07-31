package com.example.lawquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lawquiz.databinding.FragmentLoginBinding
import com.google.firebase.auth.*


private const val TAG = "LoginFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    lateinit var loginToast: Toast
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding : FragmentLoginBinding

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginToast = Toast.makeText(activity,getString(R.string.msg_login_success),Toast.LENGTH_LONG)
    }
    private fun setupViewModel() {
        loginViewModel.loggedInUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,"you r already LoggedIn",Toast.LENGTH_LONG).show()
            } ?: Toast.makeText(activity,"session ended",Toast.LENGTH_LONG).show()
        })
        loginViewModel.authResult.observe(viewLifecycleOwner, Observer {
            var toastMsg: String = ""
            if(it != null && it.isComplete){

                if(!it.isSuccessful){
                    toastMsg =
                        when(it.exception){
                            is FirebaseAuthInvalidUserException -> getString(R.string.err_msg_not_registered)
                            is FirebaseAuthWeakPasswordException -> getString(R.string.err_msg_weak_psswrd)
                            is FirebaseAuthEmailException -> getString(R.string.err_msg_email)
                            is FirebaseAuthUserCollisionException -> getString(R.string.err_msg_user_data_is_registered)
                            else -> it.exception.toString()
                        }

                }else{
                    toastMsg = getString(R.string.msg_login_success)
                }

            }else{
                toastMsg = getString(R.string.err_msg_problem_in_psswrd_or_email)
            }
            loginToast.setText(toastMsg)
            loginToast.show()
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
        loginToast.setText(R.string.sign_out_msg)
        loginToast.show();
    }

}
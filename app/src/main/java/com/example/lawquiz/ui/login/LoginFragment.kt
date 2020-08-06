package com.example.lawquiz.ui.login

import android.accounts.NetworkErrorException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lawquiz.R
import com.example.lawquiz.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseNetworkException
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

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )
        var btnLogin = binding.btnLogin
        var etxtEmail = binding.etxtEmailLogin
        var etxtpsswrd = binding.etxtPsswrdLogin
        var txtvRegister = binding.txtvRegister
        setupViewModel()
        btnLogin.isEnabled = true
        txtvRegister.isEnabled = true
        btnLogin.setOnClickListener{
            var email  = binding.etxtEmailLogin.text.toString()
            var psswrd = binding.etxtPsswrdLogin.text.toString()
            signIn(email,psswrd)
        }
        txtvRegister.setOnClickListener{
            if(!entryDataEmpty()!!)
              signUp(binding.etxtEmailLogin.text.toString(),binding.etxtPsswrdLogin.text.toString())
            else
                Toast.makeText(activity,"برجاء ادخال بيانات التسجيل كاملة",Toast.LENGTH_SHORT).show()
        }
        binding.txtvAppName.setOnClickListener{
            signOut()
        }
        //listen to changes on data on the layout and pass these changes to the layout
        etxtEmail.apply {
//            doAfterTextChanged {
//                loginViewModel.loginDataChanged(
//                    this.text.toString(),
//                    etxtpsswrd.text.toString()
//                )
//            }

           setOnFocusChangeListener{
               _,focused ->
               if(!focused){
                   loginViewModel.loginDataChanged(
                       this.text.toString(),
                       etxtpsswrd.text.toString()
                   )
               }
           }
        }

        etxtpsswrd.apply {

//           setOnFocusChangeListener{
//               _,focused ->
//               if(focused){
//                   loginViewModel.loginDataChanged(
//                       etxtEmail.text.toString(),
//                       this.text.toString()
//                   )
//               }
//           }
            doAfterTextChanged {
                loginViewModel.loginDataChanged(
                    etxtEmail.text.toString(),
                    this.text.toString()
                )
            }
            setOnEditorActionListener{_, actionId,_ ->
                when(actionId){
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.signIn(
                            etxtEmail.text.toString(),
                            this.text.toString()
                        )
                }
                false
            }
            btnLogin.setOnClickListener {
                loginViewModel.signIn(
                    etxtEmail.text.toString(),
                    this.text.toString()
                )
            }
        }
        // Inflate the layout for this fragment
        val view = binding.root
        return view
    }

    private fun entryDataEmpty(): Boolean? {
        return when{
            binding.etxtEmailLogin.text!!.trim().isEmpty() || binding.etxtPsswrdLogin.text!!.trim().isEmpty() -> true
            else -> false
        }
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
                            is NetworkErrorException -> getString(R.string.network_error)
                            is FirebaseNetworkException -> getString(R.string.network_error)
                            else -> it.exception.toString()
                        }

                }else{
                    toastMsg = getString(R.string.msg_login_success)
                    findNavController().navigate(R.id.action_loginFragment_to_mainCategoriesFragment)
                }

            }else{
                toastMsg = getString(R.string.err_msg_problem_in_psswrd_or_email)
            }
            loginToast.setText(toastMsg)
            loginToast.show()
        })
        // Observer for the network error.
         loginViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            if(loginState.isDataValid) {
                binding.btnLogin.isEnabled = true
                binding.btnLogin.alpha = 1F
            }else{
                binding.btnLogin.isEnabled = false
                binding.btnLogin.alpha = .5F
            }
            if (loginState.emailError != null) {
                binding.etxtEmailLogin.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                binding.etxtPsswrdLogin.error = getString(loginState.passwordError)
            }
        })
    }
    private fun onNetworkError() {
        if(!loginViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, getString(R.string.network_error), Toast.LENGTH_LONG).show()
            loginViewModel.onNetworkErrorShown()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
package com.example.lawquiz.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.lawquiz.ui.login.LoginStatus

@BindingAdapter("loginStatus")
fun bindStatus(progressBar: ProgressBar,
               status: LoginStatus?) {
    when (status) {
        LoginStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        LoginStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
        LoginStatus.IDLE -> {
            progressBar.visibility = View.INVISIBLE
        }
    }
}


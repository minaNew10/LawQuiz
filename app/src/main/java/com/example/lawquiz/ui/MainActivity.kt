package com.example.lawquiz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lawquiz.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()

    }
}
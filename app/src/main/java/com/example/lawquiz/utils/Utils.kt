package com.example.lawquiz.utils


import android.app.Activity
import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import com.example.lawquiz.R

fun ActionBar.setAlign(activity: Activity,title: String){
    val textView = TextView(activity)
    textView.setTypeface(null, Typeface.BOLD)
    textView.layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    textView.gravity = Gravity.RIGHT
    textView.setTextSize(20f);
    textView.setText(title)
    setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
    
    customView = textView
}
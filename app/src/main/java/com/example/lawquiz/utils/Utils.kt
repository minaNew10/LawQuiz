package com.example.lawquiz.utils


import android.app.Activity
import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
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

fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}
/**
 * ViewHolder that holds a single [TextView].
 *
 * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
 * to the RecyclerView such as where on the screen it was last drawn during scrolling.
 */
class CategoryItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
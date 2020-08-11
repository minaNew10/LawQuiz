package com.example.lawquiz.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lawquiz.R
import com.example.lawquiz.utils.CategoryItemViewHolder

class CategoriesAdapter : RecyclerView.Adapter<CategoryItemViewHolder>() {
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_categories,parent,false) as TextView
        return CategoryItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item
    }

}
package com.example.lawquiz.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lawquiz.R

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryItemViewHolder>() {
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }



    /**
     * ViewHolder that holds a single [TextView].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    class CategoryItemViewHolder private constructor(val textView: TextView): RecyclerView.ViewHolder(textView){
        companion object {
            fun from(parent: ViewGroup): CategoryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_categories, parent, false) as TextView
                return CategoryItemViewHolder(view)
            }
        }
        fun bind(
            item: String
        ) {
            textView.text = item
        }
    }


}
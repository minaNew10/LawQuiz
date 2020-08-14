package com.example.lawquiz.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lawquiz.databinding.ItemCategoriesBinding
import com.example.lawquiz.model.Category

class CategoriesAdapter(val clickListener: BranchClickListener) : RecyclerView.Adapter<CategoriesAdapter.CategoryItemViewHolder>() {
    var data = listOf<Category>()
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
        holder.bind(item,clickListener)
    }



    /**
     * ViewHolder that holds a single [TextView].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    class CategoryItemViewHolder private constructor(val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): CategoryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.item_categories, parent, false) as TextView
                val binding = ItemCategoriesBinding.inflate(layoutInflater,parent,false)
                return CategoryItemViewHolder(binding)
            }
        }

        fun bind(
            item: Category,
            clickListener: BranchClickListener
        ) {
            binding.cat= item
            binding.clickListener = clickListener

        }
    }


}
class BranchClickListener(val clickListener: (catName: Category) -> Unit) {
    fun onClick(mainCat: Category) = clickListener(mainCat)
}
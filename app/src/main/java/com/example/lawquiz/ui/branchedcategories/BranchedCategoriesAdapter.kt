package com.example.lawquiz.ui.branchedcategories;

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lawquiz.databinding.ItemBranchedCategoryBinding
import com.example.lawquiz.model.Category

class BranchedCategoriesAdapter(val clickListener: BranchedCategoriesClickListener) :
    RecyclerView.Adapter<BranchedCategoriesAdapter.BranchedCategoryViewHolder>() {
    var data = listOf<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchedCategoryViewHolder {
        return BranchedCategoryViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderMain: BranchedCategoryViewHolder, position: Int) {
        val item = data[position]
        holderMain.bind(item, clickListener)
    }


    /**
     * ViewHolder that holds a single [TextView].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    class BranchedCategoryViewHolder private constructor(val binding: ItemBranchedCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): BranchedCategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBranchedCategoryBinding.inflate(layoutInflater, parent, false)
                return BranchedCategoryViewHolder(binding)
            }
        }

        fun bind(
            item: Category,
            clickListener: BranchedCategoriesClickListener
        ) {
            binding.cat = item
            binding.clickListener = clickListener

        }
    }


}

class BranchedCategoriesClickListener(val clickListener: (item: Category) -> Unit) {
    fun onClick(item: Category) = clickListener(item)
}

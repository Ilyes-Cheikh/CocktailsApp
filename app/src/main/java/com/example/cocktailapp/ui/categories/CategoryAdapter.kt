package com.example.cocktailapp.ui.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.category.Category

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleTextView: TextView? = null

    init {
        titleTextView = itemView.findViewById(R.id.list_item_text)

    }
}

class CategoryAdapter(val context: Context, val categories: List<Category>) :
    RecyclerView.Adapter<CategoryViewHolder>() {

    interface OnCategoryItemClickListener {
        fun onCategoryItemClick(category: Category)
    }

    private var onCategoryItemClickListener: OnCategoryItemClickListener? = null

    fun setOnCategoryItemClickListener(listener: OnCategoryItemClickListener) {
        onCategoryItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categories.size
    }



    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.titleTextView?.text = category.title
        holder.itemView.setOnClickListener {
            onCategoryItemClickListener?.onCategoryItemClick(category)
        }
    }
}

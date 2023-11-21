package com.example.cocktailapp.ui.ingredients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.ingredient.Ingredient

class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleTextView: TextView? = null

    init {
        titleTextView = itemView.findViewById(R.id.list_item_text)
    }
}

class IngredientAdapter(val context: Context, val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngredientViewHolder>() {

    interface OnIngredientItemClickListener {
        fun onIngredientItemClick(ingredient: Ingredient)
    }

    private var onIngredientItemClickListener: OnIngredientItemClickListener? = null

    fun setOnIngredientItemClickListener(listener: OnIngredientItemClickListener) {
        onIngredientItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.titleTextView?.text = ingredient.title
        holder.itemView.setOnClickListener {
            onIngredientItemClickListener?.onIngredientItemClick(ingredient)
        }
    }
}

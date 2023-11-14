package com.example.cocktailapp.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Cocktails.Cocktail

class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var titleTextView : TextView? = null
    init {
        titleTextView = itemView.findViewById(R.id.textViewName)
    }

}

class CocktailAdapter (val context: Context, val cocktails : List<Cocktail>):
    RecyclerView.Adapter<CocktailViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(cocktail: Cocktail)
    }

    private var onCocktailItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onCocktailItemClickListener: OnItemClickListener) {
        this.onCocktailItemClickListener = onCocktailItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.cocktail_item_list, parent, false)
        return CocktailViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }


    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = cocktails[position]
        holder.titleTextView?.text = cocktail.titleDrink
        holder.itemView.setOnClickListener {
            onCocktailItemClickListener?.onItemClick(cocktail)
        }
    }
}
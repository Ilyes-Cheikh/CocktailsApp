package com.example.cocktailapp.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.cocktails.Cocktail
import com.example.cocktailapp.core.service.FavoritesFetcher
import com.example.cocktailapp.ui.recipe.RecipeActivity
import com.example.cocktailapp.ui.search.CocktailAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator


class FavoritesFragment : Fragment(), CocktailAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cocktailAdapter: CocktailAdapter
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        recyclerView = view.findViewById(R.id.list)
        progressIndicator = view.findViewById(R.id.progress_indicator)
        imageView = view.findViewById(R.id.empty_list_icon)
        performFromSharedPreferences()
        return view
    }
    override fun onResume() {
        super.onResume()
        performFromSharedPreferences()
    }

    private fun displayCocktails(cocktails: List<Cocktail>){
        if(isAdded){
            cocktailAdapter = CocktailAdapter(requireContext(), cocktails)
            recyclerView.adapter = cocktailAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            cocktailAdapter.setOnItemClickListener(this)
            progressIndicator.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }


    }


    private fun performFromSharedPreferences(){
        if(isAdded) {
            progressIndicator.visibility = View.VISIBLE
            progressIndicator.isIndeterminate = true
            recyclerView.visibility = View.GONE
            val favoritesFetcher = FavoritesFetcher(requireContext())
            val cocktails = favoritesFetcher.getListOfFavorites()

            if (cocktails.isEmpty()) {
                imageView.visibility = View.VISIBLE
                progressIndicator.visibility = View.GONE

                return
            }
            displayCocktails(cocktails)
        }
    }


    override fun onItemClick(cocktail: Cocktail) {
        if(isAdded) {
            val intent = Intent(requireContext(), RecipeActivity::class.java)
            intent.putExtra("id", cocktail.idDrink)
            startActivity(intent)
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesFragment()
    }
}
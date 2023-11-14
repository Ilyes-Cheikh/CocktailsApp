package com.example.cocktailapp.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.example.cocktailapp.core.service.CocktailsFetcher
import com.example.cocktailapp.core.service.FavoritesFetcher
import com.example.cocktailapp.ui.cocktails.CocktailsActivity
import com.example.cocktailapp.ui.search.CocktailAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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


    private fun displayCocktails(cocktails: List<Cocktail>){
        cocktailAdapter = CocktailAdapter(requireContext(), cocktails)
        recyclerView.adapter = cocktailAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cocktailAdapter.setOnItemClickListener(this)
        progressIndicator.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

    }


    private fun performFromSharedPreferences(){
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate= true
        recyclerView.visibility = View.GONE
        val favoritesFetcher = FavoritesFetcher(requireContext())
        val cocktails = favoritesFetcher.getListOfFavorites()

        if(cocktails.isEmpty()){
            imageView.visibility = View.VISIBLE
            progressIndicator.visibility = View.GONE

            return
        }
        displayCocktails(cocktails)
    }

    fun addCocktail(){
        val id ="16082"
        val title ="test"
        var photo ="photo"

        val cocktail = Cocktail(id, title, photo)

        val favoritesFetcher = FavoritesFetcher(requireContext())

        favoritesFetcher.addFavorite(cocktail)


    }
    override fun onItemClick(cocktail: Cocktail) {
        val intent = Intent(requireContext(), CocktailsActivity::class.java)
        intent.putExtra("id", cocktail.idDrink)

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesFragment()
    }
}
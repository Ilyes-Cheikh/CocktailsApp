package com.example.cocktailapp.ui.ingredients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Ingredient.Ingredient
import com.example.cocktailapp.core.service.IngredientFetcher
import com.example.cocktailapp.ui.cocktails.CocktailsActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator

class IngredientsFragment : Fragment() , IngredientAdapter.OnIngredientItemClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var ingredientAdapter: IngredientAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)
        recyclerView = view.findViewById(R.id.list)
        progressIndicator = view.findViewById(R.id.progress_indicator)
        performFromNetworkCall()


        return view
    }

    private fun displayIngredients(ingredients: List<Ingredient>) {
        ingredientAdapter = IngredientAdapter(requireContext(), ingredients)
        recyclerView.adapter = ingredientAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ingredientAdapter.setOnIngredientItemClickListener(this)
        recyclerView.visibility = View.VISIBLE
        progressIndicator.visibility = View.GONE
    }

    private fun performFromNetworkCall() {
        recyclerView.visibility = View.GONE
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate = true

        IngredientFetcher().fetchIngredients(
            success = { ingredients ->
                activity?.runOnUiThread {
                    displayIngredients(ingredients)
                }
            },
            failure = {
                displayNetworkCallError()
                recyclerView.isClickable = true
            }

        )
    }

    private fun displayNetworkCallError() {
        activity?.runOnUiThread {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Error")
                .setMessage("Check your internet connection")
                .setPositiveButton("Reload") { _, _ ->
                    performFromNetworkCall()
                }
                .show()
        }
    }

    override fun onIngredientItemClick(ingredient: Ingredient) {
        Log.d("ItemClicked", "Item ingredient clicked : ${ingredient.title}")
        val intent =Intent(requireContext(), CocktailsActivity::class.java)
        intent.putExtra("ingredient",ingredient.title)
        intent.putExtra("key","i")
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            IngredientsFragment()
    }
}
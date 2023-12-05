package com.example.cocktailapp.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.cocktails.Cocktail
import com.example.cocktailapp.core.model.recipe.Recipe
import com.example.cocktailapp.core.service.FavoritesFetcher
import com.example.cocktailapp.core.service.RecipeFetcher
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Picasso

class RecipeActivity : AppCompatActivity() {
    private lateinit var id : String
    private lateinit var topAppBar: androidx.appcompat.widget.Toolbar
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var recipeImage : ImageView
    private lateinit var recipeCategory : TextView
    private lateinit var recipeGlass : TextView
    private lateinit var recipeInstructions : TextView
    private lateinit var recipeIngredients : TextView
    private  lateinit var favoriteButton : ImageView
    private  lateinit var image_url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        progressIndicator = findViewById(R.id.progress_indicator)
        recipeImage = findViewById(R.id.recipe_image)
        recipeCategory = findViewById(R.id.recipe_category)
        recipeGlass = findViewById(R.id.recipe_glass)
        recipeInstructions = findViewById(R.id.recipe_instructions)
        favoriteButton = findViewById(R.id.favorite_button)
        recipeIngredients = findViewById(R.id.recipe_ingredients)

        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish()
        }
        id = intent.getStringExtra("id") ?: ""
        Log.i("RecipeActivity", "Received id: $id")
        performFromNetworkCall(id)
        if(checkFavorite(id)){
            favoriteButton.setImageResource(R.drawable.coeur)
        }
        else{
            favoriteButton.setImageResource(R.drawable.favoriteadd)
        }
        favoriteButton.setOnClickListener{
            if(checkFavorite(id)){
                favoriteButton.setImageResource(R.drawable.favoriteadd)
                removeToFavorites(id)
            }
            else{
                favoriteButton.setImageResource(R.drawable.coeur)
                AddToFavorites(id)
            }
        }
    }

    private  fun AddToFavorites(id: String){
        var cocktail = Cocktail(id,topAppBar.title.toString(),image_url)
        FavoritesFetcher(this).addFavorite(cocktail)
        MaterialAlertDialogBuilder(this)
            .setTitle("Cool ! ")
            .setMessage("This cocktail has been added to your favorites")
            .setPositiveButton("OK") { _, _ ->
            }
            .show()
    }
    private  fun removeToFavorites(id: String){
        FavoritesFetcher(this).removeFavorites(id)
        MaterialAlertDialogBuilder(this)
            .setTitle("Oops ! ")
            .setMessage("This cocktail has been removed from your favorites")
            .setPositiveButton("OK") { _, _ ->
            }
            .show()
    }

    private fun checkFavorite(id: String) : Boolean {
        return FavoritesFetcher(this).isFavorite(id)

    }

    private fun displayRecipe(recipe: List<Recipe>) {
      progressIndicator.visibility = View.GONE
        topAppBar.title = recipe[0].title
        Picasso.get().load(recipe[0].image).into(recipeImage)
        image_url = recipe[0].image.toString()
        recipeCategory.text = recipe[0].category
        recipeGlass.text = recipe[0].glass
        recipeInstructions.text = recipe[0].instructions
        val ingredientsList = mutableListOf<String>()

        for (i in 1..15) {
            val ingredient = recipe[0].getIngredient(i)
            val measure = recipe[0].getMeasure(i)

            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                val formattedIngredient = "- $ingredient ($measure)"
                ingredientsList.add(formattedIngredient)
            } else if (!ingredient.isNullOrBlank()) {
                ingredientsList.add(ingredient)
            }
        }
        val formattedIngredients = ingredientsList.joinToString("\n")
        recipeIngredients.text = formattedIngredients


    }
    fun Recipe.getIngredient(index: Int): String? {
        return when (index) {
            1 -> ingredient1
            2 -> ingredient2
            3 -> ingredient3
            4 -> ingredient4
            5 -> ingredient5
            6 -> ingredient6
            7 -> ingredient7
            8 -> ingredient8
            9 -> ingredient9
            10 -> ingredient10
            11 -> ingredient11
            12 -> ingredient12
            13 -> ingredient13
            14 -> ingredient14
            15 -> ingredient15
            else -> null
        }
    }

    fun Recipe.getMeasure(index: Int): String? {
        return when (index) {
            1 -> measure1
            2 -> measure2
            3 -> measure3
            4 -> measure4
            5 -> measure5
            6 -> measure6
            7 -> measure7
            8 -> measure8
            9 -> measure9
            10 -> measure10
            11 -> measure11
            12 -> measure12
            13 -> measure13
            14 -> measure14
            15 -> measure15
            else -> null
        }

    }
    private fun displayError() {
        runOnUiThread {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("An error has occurred")
                .setPositiveButton("OK") { _, _ ->
                    performFromNetworkCall(id)
                }
                .show()
        }
    }

    private fun performFromNetworkCall(id: String) {
        Log.i("OKHTTP", id)
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate = true
       // recyclerView.visibility = View.GONE
        RecipeFetcher().fetchRecipe(
            id,
            success = { recipe ->
                runOnUiThread {
                    displayRecipe(recipe)
                }
            },
            error = {
                displayError()

            }
        )
    }
}
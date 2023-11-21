package com.example.cocktailapp.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.example.cocktailapp.core.model.Recipe.Recipe
import com.example.cocktailapp.core.service.CocktailsFetcher
import com.example.cocktailapp.core.service.FavoritesFetcher
import com.example.cocktailapp.core.service.RecipeFetcher
import com.example.cocktailapp.ui.search.CocktailAdapter
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
    private  lateinit var favoriteButton : Button
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
        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish()
        }
        id = intent.getStringExtra("id") ?: ""
        Log.i("RecipeActivity", "Received id: $id")
        performFromNetworkCall(id)
        favoriteButton.setOnClickListener{
            AddToFavorites(id)
        }
    }

    private  fun AddToFavorites(id: String){
        var cocktail = Cocktail(id,topAppBar.title.toString(),image_url)
        FavoritesFetcher(this).addFavorite(cocktail)
    }


    private fun displayRecipe(recipe: List<Recipe>) {
      progressIndicator.visibility = View.GONE
        topAppBar.title = recipe[0].title
        Picasso.get().load(recipe[0].image).into(recipeImage)
        image_url = recipe[0].image.toString()
        recipeCategory.text = "Category : " + recipe[0].category
        recipeGlass.text ="Served in : " + recipe[0].glass
        recipeInstructions.text = "Instructions : " + recipe[0].instructions


    }

    private fun displayError() {
        runOnUiThread {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("An error has occurred")
                .setPositiveButton("OK") { dialog, which ->
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
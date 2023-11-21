package com.example.cocktailapp.core.model.Recipe

import com.example.cocktailapp.core.model.Ingredient.Ingredient
import com.google.gson.annotations.SerializedName

class RecipesResponse {
    @SerializedName("drinks")
    var recipe:List<Recipe> = emptyList()
}
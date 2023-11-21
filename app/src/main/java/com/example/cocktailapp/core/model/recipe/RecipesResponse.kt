package com.example.cocktailapp.core.model.recipe

import com.google.gson.annotations.SerializedName

class RecipesResponse {
    @SerializedName("drinks")
    var recipe:List<Recipe> = emptyList()
}
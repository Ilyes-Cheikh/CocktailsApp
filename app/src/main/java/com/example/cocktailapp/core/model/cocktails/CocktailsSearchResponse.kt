package com.example.cocktailapp.core.model.cocktails

import com.google.gson.annotations.SerializedName

class CocktailsSearchResponse {
    @SerializedName("drinks")
    var drinks: List<Cocktail>? = null
}
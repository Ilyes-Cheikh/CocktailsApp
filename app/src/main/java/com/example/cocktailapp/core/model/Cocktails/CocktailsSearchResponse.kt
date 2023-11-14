package com.example.cocktailapp.core.model.Cocktails

import com.google.gson.annotations.SerializedName

class CocktailsSearchResponse {
    @SerializedName("drinks")
    var drinks: List<Cocktail>? = null
}
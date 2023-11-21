package com.example.cocktailapp.core.model.ingredient

import com.google.gson.annotations.SerializedName

class IngredientsResponse {

    @SerializedName("drinks")
    var ingredients:List<Ingredient> = emptyList()
}
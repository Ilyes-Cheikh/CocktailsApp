package com.example.cocktailapp.core.model.Cocktails

import com.google.gson.annotations.SerializedName

class Cocktail {
    @SerializedName("idDrink")
    var idDrink: String? = null
    @SerializedName("strDrink")
    var titleDrink: String? = null
    @SerializedName("strDrinkThumb")
    var imageDrink: String? = null
}
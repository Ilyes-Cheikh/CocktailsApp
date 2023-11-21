package com.example.cocktailapp.core.model.cocktails

import com.google.gson.annotations.SerializedName

class Cocktail {
    @SerializedName("idDrink")
    var idDrink: String? = null
    @SerializedName("strDrink")
    var titleDrink: String? = null
    @SerializedName("strDrinkThumb")
    var imageDrink: String? = null

    constructor(idDrink: String?, titleDrink: String?, imageDrink: String?) {
       this.idDrink = idDrink
       this.titleDrink = titleDrink
       this.imageDrink = imageDrink
    }
}
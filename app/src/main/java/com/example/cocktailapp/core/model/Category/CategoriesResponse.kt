package com.example.cocktailapp.core.model.Category

import com.google.gson.annotations.SerializedName

class CategoriesResponse {

    @SerializedName("drinks")
    var categories: List<Category> = emptyList()

}
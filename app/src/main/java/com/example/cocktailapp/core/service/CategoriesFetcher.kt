package com.example.cocktailapp.core.service

import com.example.cocktailapp.core.model.Category

class CategoriesFetcher {
    //API Route
    //GET
    fun fetchCategories(success: (List<Category>) -> Unit, failure :(Error) -> Unit) {  //unit : void
        //OKHTTP
        success(emptyList())


    }
}
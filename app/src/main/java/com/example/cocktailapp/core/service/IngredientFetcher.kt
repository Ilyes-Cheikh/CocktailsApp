package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.ingredient.Ingredient
import com.example.cocktailapp.core.model.ingredient.IngredientsResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.io.IOException

class IngredientFetcher {
    var url : String = "https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list"

    fun fetchIngredients(success: (List<Ingredient>) -> Unit, failure :(Error) -> Unit) {  //unit : void

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.i("OKHTTP for ingredients", "ONFailure")
                failure(Error(e.message))
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.i("OKHTTP", "ONSucces")
                val gson: Gson = Gson()
                val ingredientsResponse =gson.fromJson(response.body?.string(), IngredientsResponse::class.java)
                Log.i("OKHTTP","count ingredients =${ingredientsResponse.ingredients.count()}")
                success(ingredientsResponse.ingredients)


            }
        })



    }
}
package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.recipe.Recipe
import com.example.cocktailapp.core.model.recipe.RecipesResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient

class RecipeFetcher {
    private var baseUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="

    private fun generateURL(id: String): String {
        return "${baseUrl}$id"
    }

    fun fetchRecipe(id: String, success: (List<Recipe>) -> Unit, error: (Error) -> Unit) {
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url(generateURL(id))
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.i("OKHTTP", "ONSucces")
                val gson: Gson = Gson()
                val recipesResponse = gson.fromJson(response.body?.string(), RecipesResponse::class.java)
                success(recipesResponse.recipe ?: emptyList())
            }

            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                Log.i("OKHTTP", "ONFailure")
                error(Error(e.message))
            }
        })
    }
}
package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.cocktails.Cocktail
import com.example.cocktailapp.core.model.cocktails.CocktailsSearchResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient

class CocktailsFetcher {

    private var baseUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/"
    private var url: String = ""

    private fun generateURL(key: String, data: String): String {
        return when (key) {
            "s" -> "${baseUrl}search.php?s=$data"
            "c" -> "${baseUrl}filter.php?c=$data"
            "i" -> "${baseUrl}filter.php?i=$data"
            "alcoholic" -> "${baseUrl}filter.php?a=Alcoholic"
            "nonalcoholic" -> "${baseUrl}filter.php?a=Non_Alcoholic"
            "random" -> "${baseUrl}random.php"
            else -> "${baseUrl}search.php?s=margarita"
        }
    }

    fun fetchCocktails(data: String, key: String, success: (List<Cocktail>) -> Unit, error: (Error) -> Unit) {
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url(generateURL(key, data))
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.i("OKHTTP", "ONSucces")
                val gson: Gson = Gson()
                val cocktailsSearchResponse = gson.fromJson(response.body?.string(), CocktailsSearchResponse::class.java)
                success(cocktailsSearchResponse.drinks ?: emptyList())
            }

            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                Log.i("OKHTTP", "ONFailure")
                error(Error(e.message))
            }
        })
    }
}

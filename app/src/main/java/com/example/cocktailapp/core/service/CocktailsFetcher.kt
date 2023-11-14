package com.example.cocktailapp.core.service
import com.google.gson.Gson
import android.util.Log
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.example.cocktailapp.core.model.Cocktails.CocktailsSearchResponse
import okhttp3.OkHttpClient

class CocktailsFetcher {
    private var url : String = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita"
    fun fetchCocktails(success: (List<Cocktail>) -> Unit, error: (Error) -> Unit) {
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.i("OKHTTP", "ONSucces")
                val gson: Gson = Gson()
                val cocktailsSearchResponse= gson.fromJson(response.body?.string(), CocktailsSearchResponse::class.java)
                success(cocktailsSearchResponse.drinks!!)
            }
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                Log.i("OKHTTP", "ONFailure")
                error(Error(e.message))
            }
        })
    }
}
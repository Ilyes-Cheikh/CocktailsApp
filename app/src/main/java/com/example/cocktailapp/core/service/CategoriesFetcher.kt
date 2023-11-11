package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.Category.CategoriesResponse
import com.example.cocktailapp.core.model.Category.Category
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.io.IOException

class CategoriesFetcher {

    var url : String = "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list"

    fun fetchCategories(success: (List<Category>) -> Unit, failure :(Error) -> Unit) {  //unit : void

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.i("OKHTTP", "ONFailure")
                failure(Error(e.message))
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.i("OKHTTP", "ONSucces")
                val gson: Gson = Gson()
                val categoriesResponse =gson.fromJson(response.body?.string(), CategoriesResponse::class.java)
                Log.i("OKHTTP","count =${categoriesResponse.categories.count()}")
                success(categoriesResponse.categories)

            }
        })



    }
}
package com.example.cocktailapp.core.service

import android.content.SharedPreferences
import android.content.Context
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesFetcher(private val context: Context) {
    companion object{
        const val SharedPreferencesKey ="cocktailsFavorites"
    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SharedPreferencesKey, Context.MODE_PRIVATE)

    private val gson = Gson()
     fun addFavorite(cocktail: Cocktail){
        val list = getListOfFavorites().toMutableList()
        list.add(cocktail)
        val listJson = gson.toJson(list)
        sharedPreferences.edit().putString(SharedPreferencesKey, listJson).apply()

    }

    fun getListOfFavorites(): List<Cocktail>{
        val listJson = sharedPreferences.getString(SharedPreferencesKey, null)
        return if (listJson != null) {
            val type = object : TypeToken<List<Cocktail>>() {}.type
            gson.fromJson(listJson, type)
        } else {
            emptyList()
        }
    }

    fun removeFavorites(id:String){
        val list = getListOfFavorites().toMutableList()
        list.removeIf { it.idDrink == id }
        val listJson = gson.toJson(list)
        sharedPreferences.edit().putString(SharedPreferencesKey, listJson).apply()

    }

    fun isFavorite(id: String): Boolean{
        val list = getListOfFavorites()
        return list.any { it.idDrink == id }
    }

}
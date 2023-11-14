package com.example.cocktailapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktailapp.ui.categories.CategoriesFragment
import com.example.cocktailapp.ui.favorites.FavoritesFragment
import com.example.cocktailapp.ui.ingredients.IngredientsFragment
import com.example.cocktailapp.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.cocktailapp.ui.alcohol.AlcoholFragment

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: BottomNavigationView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tab_layout)
        displaySearchFragment()
        navigate()
    }

    private fun displaySearchFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, SearchFragment.newInstance())
            .commit()
    }
    private fun displayCategoriesFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, CategoriesFragment.newInstance())
            .commit()
    }
    private fun displayIngredientsFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, IngredientsFragment.newInstance())
            .commit()
    }

    private fun displayAlcoholFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, AlcoholFragment.newInstance())
            .commit()
    }
    private fun displayFavoritesFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, FavoritesFragment.newInstance())
            .commit()
    }

    private  fun navigate(){
        tabLayout.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search_page -> {
                    displaySearchFragment()
                    true
                }
                R.id.categories_page -> {
                    displayCategoriesFragment()
                    true
                }
                R.id.ingredients_page -> {
                    displayIngredientsFragment()
                    true
                }
                R.id.favorites_page -> {
                    displayFavoritesFragment()
                    true
                }
                R.id.alcohol_page -> {
                    displayAlcoholFragment()
                    true
                }
                else -> false
            }
        }
    }

}
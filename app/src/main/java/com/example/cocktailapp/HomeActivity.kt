package com.example.cocktailapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cocktailapp.ui.categories.CategoriesFragment
import com.example.cocktailapp.ui.favorites.FavoritesFragment
import com.example.cocktailapp.ui.ingredients.IngredientsFragment
import com.example.cocktailapp.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.cocktailapp.ui.alcohol.AlcoholFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var tabLayout: BottomNavigationView
    private var currentFragmentTag: String? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        tabLayout = findViewById(R.id.tab_layout)
        displayFragment(SearchFragment.newInstance(),"search_fragment")
        navigate()
    }

    private fun displayFragment(fragment: Fragment, tag: String){
        if (tag != currentFragmentTag) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment, tag)
                .commit()

            currentFragmentTag = tag
        }
    }


    private  fun navigate(){
        tabLayout.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search_page -> {
                    displayFragment(SearchFragment.newInstance(),"search_fragment")
                    true
                }
                R.id.categories_page -> {
                    displayFragment(CategoriesFragment.newInstance(),"categories_fragment")
                    true
                }
                R.id.ingredients_page -> {
                    displayFragment(IngredientsFragment.newInstance(),"ingredients_fragment")
                    true
                }
                R.id.favorites_page -> {
                    displayFragment(FavoritesFragment.newInstance(),"favorites_fragment")
                    true
                }
                R.id.alcohol_page -> {
                    displayFragment(AlcoholFragment.newInstance(),"alcohol_fragment")
                    true
                }
                else -> false
            }
        }
    }

}
package com.example.cocktailapp.ui.cocktails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cocktailapp.R

class CocktailsActivity : AppCompatActivity() {
    private lateinit var topAppBar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails)

        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        val key = intent.getStringExtra("key")
        val data = intent.getStringExtra("data")

        Log.i("CocktailsActivity", "Received Some Data: $key")
        Log.i("CocktailsActivity", "Received Some Data: $data")


    }


}
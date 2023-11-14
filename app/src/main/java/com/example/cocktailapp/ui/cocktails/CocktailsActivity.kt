package com.example.cocktailapp.ui.cocktails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.example.cocktailapp.core.service.CocktailsFetcher
import com.example.cocktailapp.ui.search.CocktailAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator

class CocktailsActivity : AppCompatActivity(), CocktailAdapter.OnItemClickListener {
    private lateinit var topAppBar: androidx.appcompat.widget.Toolbar
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var recyclerView: RecyclerView
    private lateinit var cocktailAdapter: CocktailAdapter
    private lateinit var key: String
    private lateinit var data: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails)

        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        key = intent.getStringExtra("key") ?: ""
        data = intent.getStringExtra("data") ?: ""

        Log.i("CocktailsActivity", "Received Key: $key")
        Log.i("CocktailsActivity", "Received Data: $data")

        recyclerView = findViewById(R.id.list)
        progressIndicator = findViewById(R.id.progress_indicator)

        performFromNetworkCall(data, key)
    }

    private fun displayCocktails(cocktails: List<Cocktail>) {
        cocktailAdapter = CocktailAdapter(this, cocktails)
        recyclerView.adapter = cocktailAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        cocktailAdapter.setOnItemClickListener(this)
        progressIndicator.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun displayError() {
        runOnUiThread {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("An error has occurred")
                .setPositiveButton("OK") { dialog, which ->
                    performFromNetworkCall(data, key)
                }
                .show()
        }
    }

    private fun performFromNetworkCall(data: String, key: String) {
        Log.i("OKHTTP", data)
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate = true
        recyclerView.visibility = View.GONE
        CocktailsFetcher().fetchCocktails(
            data,
            key,
            success = { cocktails ->
                runOnUiThread {
                    displayCocktails(cocktails)
                }
            },
            error = {
                displayError()
                recyclerView.isClickable = true
            }
        )
    }

    override fun onItemClick(cocktail: Cocktail) {
        // Handle item click event here
    }
}

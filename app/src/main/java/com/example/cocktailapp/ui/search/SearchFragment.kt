package com.example.cocktailapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.example.cocktailapp.core.service.CocktailsFetcher
import com.example.cocktailapp.ui.recipe.RecipeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText


class SearchFragment : Fragment() , CocktailAdapter.OnItemClickListener {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var searchAdapter: CocktailAdapter
    private  lateinit var progressIndicator: CircularProgressIndicator

    private  lateinit var  cocktail_image : ImageView
    private lateinit var  searchText: TextInputEditText
    private  lateinit var  searchButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.list)
        progressIndicator = view.findViewById(R.id.progress_indicator)
        searchText = view.findViewById(R.id.searchpage_textinputeditfile)
        searchButton = view.findViewById(R.id.searchpage_button)
        searchButton.setOnClickListener{

            if (searchText.text.toString().isEmpty()){
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Error")
                    .setMessage("Please enter a search term")
                    .setPositiveButton("OK") { dialog, which ->

                    }
                    .show()
                return@setOnClickListener
            }
            performFromNetworkCall(searchText.text.toString())
        }

        return view
    }

    private fun displayCocktails(cocktails: List<Cocktail>){
        searchAdapter = CocktailAdapter(requireContext(), cocktails)
        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter.setOnItemClickListener(this)
        progressIndicator.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

    }

    private  fun displayError(){
        activity?.runOnUiThread{
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Error")
                .setMessage("An error has occurred")
                .setPositiveButton("OK") { _, _ ->
                    performFromNetworkCall(searchText.text.toString())
                }
                .show()
        }
    }
    private fun performFromNetworkCall(search_text : String ){
        Log.i("OKHTTP", search_text)
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate= true
        recyclerView.visibility = View.GONE
        CocktailsFetcher().fetchCocktails(
            search_text,
            "s",
            success = { cocktails ->
               activity?.runOnUiThread {
                    if(cocktails.isEmpty()){
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Information")
                            .setMessage("No cocktails found")
                            .setPositiveButton("OK") { _, _ ->

                            }
                            .show()
                        progressIndicator.visibility = View.GONE

                        return@runOnUiThread
                    }
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
        Log.d("ItemClicked", "Item clicked cocktail ${cocktail.titleDrink}")
        val intent = Intent(requireContext(), RecipeActivity::class.java)
        intent.putExtra("id",cocktail.idDrink)
        startActivity(intent)
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}
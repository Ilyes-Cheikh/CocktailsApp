package com.example.cocktailapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.Cocktails.Cocktail
import com.example.cocktailapp.core.service.CocktailsFetcher
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText


class SearchFragment : Fragment() , CocktailAdapter.OnItemClickListener {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var searchAdapter: CocktailAdapter
    private  lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var  search_text: TextInputEditText
    private  lateinit var  search_button : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.list)
        progressIndicator = view.findViewById(R.id.progress_indicator)
        search_text = view.findViewById(R.id.searchpage_textinputeditfile)
        search_button = view.findViewById(R.id.searchpage_button)
        search_button.setOnClickListener{
            performFromNetworkCall(search_text.text.toString())
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
                .setPositiveButton("OK") { dialog, which ->
                    performFromNetworkCall(search_text.text.toString())
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
            success = { cocktails ->
               activity?.runOnUiThread {
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

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}
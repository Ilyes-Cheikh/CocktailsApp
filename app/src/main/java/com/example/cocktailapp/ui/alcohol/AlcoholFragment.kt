package com.example.cocktailapp.ui.alcohol

import android.content.Intent
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
import com.example.cocktailapp.ui.cocktails.CocktailsActivity
import com.example.cocktailapp.ui.search.CocktailAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText


class AlcoholFragment : Fragment(), CocktailAdapter.OnItemClickListener {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var searchAdapter: CocktailAdapter
    private  lateinit var progressIndicator: CircularProgressIndicator
    private  lateinit var  alcoholButton : Button
    private  lateinit var  noAlcoholButton : Button

    private lateinit var btnClick: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_aclohol, container, false)

        recyclerView = view.findViewById(R.id.list)
        progressIndicator = view.findViewById(R.id.progress_indicator)

        alcoholButton = view.findViewById(R.id.btnAlcohol)
        noAlcoholButton = view.findViewById(R.id.btnNonAlcohol)

        alcoholButton.setOnClickListener{
            btnClick = "alcoholic"
            performFromNetworkCall("alcoholic")
        }

        noAlcoholButton.setOnClickListener{
            btnClick = "nonalcoholic"
            performFromNetworkCall("nonalcoholic")
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
                    performFromNetworkCall(btnClick)
                }
                .show()
        }
    }
    private fun performFromNetworkCall(key : String ){
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate= true
        recyclerView.visibility = View.GONE
        CocktailsFetcher().fetchCocktails(
            "",
            key,
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
        val intent = Intent(requireContext(), CocktailsActivity::class.java)
        intent.putExtra("id", cocktail.idDrink)
    }




    companion object {

        fun newInstance() =
            AlcoholFragment()
    }
}
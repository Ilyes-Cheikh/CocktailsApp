package com.example.cocktailapp.ui.categories

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.category.Category
import com.example.cocktailapp.core.service.CategoriesFetcher
import com.example.cocktailapp.ui.cocktails.CocktailsActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator


class CategoriesFragment : Fragment(), CategoryAdapter.OnCategoryItemClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        recyclerView = view.findViewById(R.id.list)
        progressIndicator = view.findViewById(R.id.progress_indicator)
        performFromNetworkCall()


        return view
    }

    private fun displayCategories(categories: List<Category>) {

        if(isAdded){
            categoryAdapter = CategoryAdapter(requireContext(), categories)
            recyclerView.adapter = categoryAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            categoryAdapter.setOnCategoryItemClickListener(this)
            recyclerView.visibility = View.VISIBLE
            progressIndicator.visibility = View.GONE
        }

    }

    private fun performFromNetworkCall() {
        recyclerView.visibility = View.GONE
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.isIndeterminate = true

        CategoriesFetcher().fetchCategories(
            success = { categories ->
                activity?.runOnUiThread {
                    displayCategories(categories)
                }
            },
            failure = {
                displayNetworkCallError()
                recyclerView.isClickable = true
            }

        )
    }

    private fun displayNetworkCallError() {
        if(isAdded){
            activity?.runOnUiThread {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Error")
                    .setMessage("Check your internet connection")
                    .setPositiveButton("Reload") { _, _ ->
                        performFromNetworkCall()
                    }
                    .show()
            }
        }

    }

    override fun onCategoryItemClick(category: Category) {
        if(isAdded){
            Log.d("ItemClicked", "Item clicked category ${category.title}")
            val intent = Intent(requireContext(), CocktailsActivity::class.java)
            intent.putExtra("data",category.title)
            intent.putExtra("key","c")
            startActivity(intent)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}

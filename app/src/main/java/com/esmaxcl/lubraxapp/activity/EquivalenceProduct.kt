package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.esmaxcl.lubraxapp.R
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.esmaxcl.lubraxapp.adapter.*
import com.esmaxcl.lubraxapp.model.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_equivalence_product.*
import okhttp3.*
import java.io.IOException

class EquivalenceProduct : AppCompatActivity() {

    var displayProductsList: MutableList<ProductList> = mutableListOf()
    var productList: MutableList<ProductList> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equivalence_product)

        this.title = getString(R.string.productos)
        val intent = intent
        val b = intent.extras

        recyclerView_competitors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_competitors.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        if (b != null) {
            val competitorID = b.getInt("competitorID",0)
            fetchProducts(competitorID)
        }
    }

    private fun fetchProducts(competitorID: Int) {

        animation_view_competitors.visibility = View.VISIBLE
        println("Attempting to Fetch JSON")

        val url = "$BASE_URL/webservice/company/$competitorID"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()
                val gson = GsonBuilder().create()

                val response = gson.fromJson(body, CompetitorProductFeed::class.java)
                animation_view_competitors.visibility = View.INVISIBLE

                if (response.status) {

                    response.company_product.forEach {
                        displayProductsList.add(it)
                        productList.add(it)
                    }
                    runOnUiThread {
                        recyclerView_competitors.adapter = EquivalenceProductAdapter(displayProductsList)
                    }
                } else {
                    println("Failed Request : $url")
                    println(response)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
                animation_view_competitors.visibility = View.INVISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.tollbar_menu, menu)
        val searchItem = menu!!.findItem(R.id.action_search)

        if (searchItem != null) {

            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {

                    println(p0)

                    if (p0!!.isNotEmpty()) {

                        displayProductsList.clear()
                        val search = p0.toLowerCase()

                        productList.forEach {
                            if(it.name.toLowerCase().contains(search)){
                                displayProductsList.add(it)
                            }
                        }

                        recyclerView_competitors.adapter!!.notifyDataSetChanged()

                    } else {

                        displayProductsList.clear()
                        displayProductsList.addAll(productList)
                        recyclerView_competitors.adapter!!.notifyDataSetChanged()

                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}

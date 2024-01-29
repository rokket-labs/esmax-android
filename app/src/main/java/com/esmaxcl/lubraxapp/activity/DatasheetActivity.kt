package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.adapter.DatasheetAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_datasheet.*
import okhttp3.*
import java.io.IOException
import com.esmaxcl.lubraxapp.model.BASE_URL
import com.esmaxcl.lubraxapp.model.CProduct
import com.esmaxcl.lubraxapp.model.ProductFeed


 class DatasheetActivity : AppCompatActivity() {

    var displayProductsList: MutableList<CProduct> = mutableListOf()
    var productList: MutableList<CProduct> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datasheet)


        this.title = this.getString(R.string.ficha_tecnica)

        recyclerView_datasheet.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_datasheet.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        fetchProducts()
    }

    private fun fetchProducts() {

        animation_view_datasheet.visibility = View.VISIBLE
        println("Attempting to Fetch JSON")

        val url = "$BASE_URL/webservice/product/?unique=1"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()
                val gson = GsonBuilder().create()

                val competitorProductFeed = gson.fromJson(body, ProductFeed::class.java)
                animation_view_datasheet.visibility = View.INVISIBLE

                if (competitorProductFeed.status) {


                    competitorProductFeed.product.forEach {
                        displayProductsList.add(it)
                        productList.add(it)
                    }

//                    displayProductsList.add() = competitorProductFeed.product
//                    productList = competitorProductFeed.product

                    runOnUiThread {
                        recyclerView_datasheet.adapter = DatasheetAdapter(displayProductsList)
                    }
                } else {
                    println("Failed Request : $url")
                    println(response)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
                animation_view_datasheet.visibility = View.INVISIBLE
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
                        recyclerView_datasheet.adapter!!.notifyDataSetChanged()

                    } else {

                        displayProductsList.clear()
                        displayProductsList.addAll(productList)
                        recyclerView_datasheet.adapter!!.notifyDataSetChanged()

                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}

package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.adapter.VehicleProductAdapter
import com.esmaxcl.lubraxapp.model.BASE_URL
import com.esmaxcl.lubraxapp.model.ProductFeed
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_select_vehicle_product.*
import okhttp3.*
import java.io.IOException

class SelectVehicleProductActivity : AppCompatActivity() {

    var productIndex: MutableList<Any> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_vehicle_product)

        this.title = getString(R.string.productos)

        recyclerView_vproduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_vproduct.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val intent = intent
        val b = intent.extras

        if (b != null) {
            val manufacture = b.get("manufactureID") as String
            val category = b.get("categoryID") as Int
            val model = b.get("modelID") as String
            val type = b.get("typeID") as String

            val urlPath = "$BASE_URL/webservice/product/filter/?category=$category&manufacture=$manufacture&model=$model&type=$type"
            println(urlPath)
            fetchContent(urlPath)
        }
    }

    private fun fetchContent(urlPath: String? = null) {

        if (urlPath != null) {

            animation_view_vproduct.visibility = View.VISIBLE
            println("Attempting to Fetch JSON")

            val request = Request.Builder().url(urlPath).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {

                    val body = response?.body()?.string()
                    val gson = GsonBuilder().create()

                    val responsejson = gson.fromJson(body, ProductFeed::class.java)
                    animation_view_vproduct.visibility = View.INVISIBLE

                    val wordArray = ArrayList<String>()

                    responsejson.product.forEach { row ->

                        val category = row.engine.name.toUpperCase()

                        if (!wordArray.contains(category)) {
                            wordArray.add(category)
                            productIndex.add(category)
                        }
                        productIndex.add(row)
                    }


                    if (responsejson.status) {
                        runOnUiThread {
//                            recyclerView_vproduct.adapter = VehicleProductAdapter(response.product)

                            recyclerView_vproduct.adapter = VehicleProductAdapter(productIndex)
                        }
                    } else {
                        println("Failed Request : $urlPath")
                        println(response)
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                    animation_view_vproduct.visibility = View.INVISIBLE
                }
            })
        }
    }
}

package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.adapter.VehicleBrandAdapter
import com.esmaxcl.lubraxapp.model.BASE_URL
import com.esmaxcl.lubraxapp.model.VehicleBrand

import com.esmaxcl.lubraxapp.model.VehicleBrandResponse
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_select_vehicle_brand.*
import okhttp3.*
import java.io.IOException


class SelectVehicleBrandActivity : AppCompatActivity() {

    var brandList: List<VehicleBrand> = emptyList()
    var brandIndex: MutableList<Any> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_vehicle_brand)

        this.title = getString(R.string.tipo_marca)
        recyclerView_brand.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_brand.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val intent = intent
        val b = intent.extras

        if (b != null) {
            val category = b.get("categoryID") as Int
            val path = "$BASE_URL/webservice/product/filter/?category=$category"
            fetchContent(path, category)
        }
    }

    private fun fetchContent(urlPath: String? = null, categoryID: Int? = null) {

        if (urlPath != null) {

            animation_view_brand.visibility = View.VISIBLE
            println("Attempting to Fetch JSON")

            val request = Request.Builder().url(urlPath).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {

                    val body = response?.body()?.string()
                    val gson = GsonBuilder().create()

                    val response = gson.fromJson(body, VehicleBrandResponse::class.java)
                    animation_view_brand.visibility = View.INVISIBLE

                    // brandList = response.manufacture
                    val wordArray = ArrayList<String>()

                    response.manufacture.forEach { row ->

                        val category = row.brand.get(0).toString().toUpperCase()

                        if (wordArray.contains(category).equals(false)) {
                            wordArray.add(category)
                            brandIndex.add(category)
                        }
                        brandIndex.add(row)
                    }

                    println(wordArray)

                    if (response.status) {
                        runOnUiThread {
                            println(brandIndex.size)
                            //recyclerView_brand.adapter = VehicleBrandAdapter(response.manufacture, categoryID)
                            recyclerView_brand.adapter = VehicleBrandAdapter(brandIndex, categoryID)

                        }
                    } else {
                        println("Failed Request : $urlPath")
                        println(response)
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                    animation_view_brand.visibility = View.INVISIBLE
                }
            })
        }
    }
}



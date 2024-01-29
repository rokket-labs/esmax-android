package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.adapter.VehicleModelAdapter
import com.esmaxcl.lubraxapp.model.BASE_URL
import com.esmaxcl.lubraxapp.model.VehicleModelResponse
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_select_vehicle_model.*
import okhttp3.*
import java.io.IOException

class SelectVehicleModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_vehicle_model)

        this.title = getString(R.string.tipo_modelo)

        recyclerView_model.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_model.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val intent = intent
        val b = intent.extras

        if (b != null) {
            val manufacture = b.get("manufactureID") as String
            val category = b.get("categoryID") as Int
            val path = "$BASE_URL/webservice/product/filter/?category=$category&manufacture=$manufacture"
            fetchContent(path, category, manufacture)
        }
    }

    private fun fetchContent(urlPath: String? = null,
                             category: Int? = null,
                             manufacture: String? = null) {

        if(urlPath != null){

            animation_view_model.visibility = View.VISIBLE
            println("Attempting to Fetch JSON")

            val request = Request.Builder().url(urlPath).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {

                    val body = response?.body()?.string()
                    val gson = GsonBuilder().create()

                    val response = gson.fromJson(body, VehicleModelResponse::class.java)
                    animation_view_model.visibility = View.INVISIBLE

                    if (response.status) {
                        runOnUiThread {
                            recyclerView_model.adapter = VehicleModelAdapter(response.model, category, manufacture)
                        }
                    } else {
                        println("Failed Request : $urlPath")
                        println(response)
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                    animation_view_model.visibility = View.INVISIBLE
                }
            })
        }
    }
}

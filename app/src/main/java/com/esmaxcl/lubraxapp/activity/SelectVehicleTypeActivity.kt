package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.adapter.VehicleTypeAdapter
import com.esmaxcl.lubraxapp.model.BASE_URL
import com.esmaxcl.lubraxapp.model.VehicleTypeResponse
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_select_vehicle_type.*
import okhttp3.*
import java.io.IOException

class SelectVehicleTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_vehicle_type)

        this.title = getString(R.string.tipo_motor)

        recyclerView_type.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_type.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val intent = intent
        val b = intent.extras

        if (b != null) {
            val manufacture = b.get("manufactureID") as String
            val category = b.get("categoryID") as Int
            val model = b.get("modelID") as String

            val urlPath = "$BASE_URL/webservice/product/filter/?category=$category&manufacture=$manufacture&model=$model"
            fetchContent(urlPath, category, manufacture, model)
        }
    }

    private fun fetchContent(
        urlPath: String? = null,
        category: Int? = null,
        manufacture: String? = null,
        model: String? = null
    ) {

        if (urlPath != null) {

            animation_view_type.visibility = View.VISIBLE
            println("Attempting to Fetch JSON")

            val request = Request.Builder().url(urlPath).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {

                    val body = response?.body()?.string()
                    val gson = GsonBuilder().create()

                    val responsejson = gson.fromJson(body, VehicleTypeResponse::class.java)
                    animation_view_type.visibility = View.INVISIBLE

                    if (responsejson.status) {
                        runOnUiThread {
                            recyclerView_type.adapter = VehicleTypeAdapter(
                                responsejson.type,
                                category,
                                manufacture,
                                model
                            )
                        }
                    } else {
                        println("Failed Request : $urlPath")
                        println(responsejson)
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("Failed to execute request")
                    animation_view_type.visibility = View.INVISIBLE
                }
            })
        }


    }
}

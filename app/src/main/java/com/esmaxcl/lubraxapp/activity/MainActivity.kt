package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.esmaxcl.lubraxapp.R

import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

import com.esmaxcl.lubraxapp.model.*
import com.esmaxcl.lubraxapp.adapter.*
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    var productList: MutableList<CProduct> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        recyclerView_products.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_menu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        fetchProducts()
        displayMenu()
    }

    private fun fetchProducts() {
        val url = "$BASE_URL/webservice/product/?limit=6&random=1"
        println(url)

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val response = gson.fromJson(body, ProductResponse::class.java)

                val productDb: List<Book> = Book.listAll(Book::class.java)
//                Book.deleteAll(Book::class.java)

                // Step on Local Products
                println("total products: " + productDb.size)

                if (productDb.isEmpty()) {

                    println("product is empty, add remote products")

                    response.product.forEach { row ->
                        val gson = Gson()

                        // convert from object to JSON
                        val book = Book(row.unique_key, gson.toJson(row))
                        book.save()

                        println("[] saved book " + row.unique_key)

                        // append product
                        productList.add(row)
                        println("appending key remote: "+ row.unique_key)

                    }
                } else {
                    println("showing local database product")
                    productDb.forEach { row ->
                        val gson = Gson()
                        // convert from json to Object
                        val productObject = gson.fromJson(row.product, CProduct::class.java)
                        // append product
                        productList.add(productObject)
                        println("appending key local: "+ row.key.toString())
                    }
                }

                println(response.status)

                if (response.status) {
                    runOnUiThread {
                        recyclerView_products.adapter = HeaderProductAdapter(productList)
                    }
                } else {
                    println("Favorite Product Request Failed")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request $e")
            }
        })
    }

    private fun displayMenu() {

        val options: Array<MenuOptions> = arrayOf(
            MenuOptions(
                1,
                R.drawable.search,
                getString(R.string.buscador_lubricantes),
                getString(R.string.buscador_lubricantes_sub)
            ),
            MenuOptions(
                2,
                R.drawable.datasheet,
                getString(R.string.ficha_tecnica),
                getString(R.string.fichas_tecnicas_sub)
            ),
            MenuOptions(
                3,
                R.drawable.table,
                getString(R.string.tabla_equivalencias),
                getString(R.string.tabla_equivalencias_sub)
            ),
            MenuOptions(
                4,
                R.drawable.faq,
                getString(R.string.preguntas_frecuentes),
                getString(R.string.preguntas_frecuentes_sub)
            )
        )

        runOnUiThread {
            recyclerView_menu.adapter = MenuAdapter(options)
        }
    }
}


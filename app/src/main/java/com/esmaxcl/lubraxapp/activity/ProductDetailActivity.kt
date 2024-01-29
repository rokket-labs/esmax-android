package com.esmaxcl.lubraxapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_product_detail.*
import com.esmaxcl.lubraxapp.R
import com.squareup.picasso.Picasso
import com.esmaxcl.lubraxapp.adapter.*
import com.esmaxcl.lubraxapp.model.*
import android.net.Uri
import com.google.gson.Gson


class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        recyclerView_formats.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_uses.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val intent = this.intent
        val bundle = intent.extras

        if (bundle != null) {

            val myProduct = bundle.getSerializable("product") as CProduct

            this.title = myProduct.name

            val productDb: List<Book> = Book.listAll(Book::class.java)

            // Step on Local Products
            println("total products: " + productDb.size)

            if (productDb.isEmpty()) {

                val gson = Gson()
                val book = Book(myProduct.unique_key, gson.toJson(myProduct))
                book.save()
                println("[] saved book " + myProduct.unique_key)

            } else {
                if (productDb.size >= 5) {

                    // Delete first added
                    val book: Book = Book.findById(Book::class.java, productDb.first().id)
                    book.delete()
                    println("product removed")

                    // check it does not exist.
                    val searchQuerySet = Book.find(Book::class.java, "key = ?", myProduct.unique_key)

                    if (searchQuerySet.isEmpty()) {
                        val gson = Gson()
                        val newbook = Book(myProduct.unique_key, gson.toJson(myProduct))
                        newbook.save()
                        println("book " + myProduct.unique_key + " saved")
                    } else {
                        println("book "+ myProduct.unique_key + " not saved (exists)")
                    }

                } else {
                    productDb.forEach { row ->
                        // Check product does not exist
                        if (row.key != myProduct.unique_key) {
                            // Save To Database
                            val gson = Gson()
                            val book = Book(myProduct.unique_key, gson.toJson(myProduct))
                            book.save()
                            println("book " + row.key + " already exists (not saved)")
                        }
                        println("saved book " + row.key)
                    }
                }
            }


            // Load Remote Image URL
            val thumbnailImageView = imageView_product
            Picasso.with(imageView_product.context).load(myProduct.image).into(thumbnailImageView)


            textView_name.text = myProduct.name
            textView_engine.text = myProduct.engine.name
            textView_description.text = myProduct.description

            runOnUiThread {
                recyclerView_formats.adapter = FormatAdapter(myProduct.formats)
            }

            runOnUiThread {
                recyclerView_uses.adapter = UseAdapter(myProduct.uses)
            }

            button_pdf.setOnClickListener {

                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(myProduct.pdf))
                startActivity(browserIntent)
            }
        }
    }
}

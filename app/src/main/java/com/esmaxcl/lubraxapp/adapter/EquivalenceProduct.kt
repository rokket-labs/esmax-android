package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.ProductDetailActivity
import com.esmaxcl.lubraxapp.model.CProduct
import com.esmaxcl.lubraxapp.model.ProductList
import kotlinx.android.synthetic.main.competitor_product_row.view.*

class EquivalenceProductAdapter(private val products: List<ProductList>) : RecyclerView.Adapter<ProductViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.competitor_product_row, parent, false)
        return ProductViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // Select Current Faq
        val productObject = products[position]
        // Set layout values
        holder.view.textview_name.text = productObject.name
        // Set Faq Object
        holder.product = productObject.product
    }
}

class ProductViewHolder(val view: View, var product: CProduct? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (product != null) {
                println("Intent: Product Detail")
                println(product!!.name)

                /* Intent: Product Detail */
                val intent = Intent(view.context, ProductDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("product", product)
                intent.putExtras(bundle)
                view.context.startActivity(intent)
            }
        }
    }
}
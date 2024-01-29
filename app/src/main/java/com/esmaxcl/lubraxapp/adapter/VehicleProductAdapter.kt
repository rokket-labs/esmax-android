package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.ProductDetailActivity
import com.esmaxcl.lubraxapp.model.CProduct
import kotlinx.android.synthetic.main.product_row.view.*
import kotlinx.android.synthetic.main.header_layout.view.*



class VehicleProductAdapter(private val product: MutableList<Any>) : RecyclerView.Adapter<VehicleViewHolder>() {

    private val INDEX = 0
    private val BRAND = 1

    // numberOfItems
    override fun getItemCount(): Int {
        return product.count()
    }

    override fun getItemViewType(position: Int): Int {
        val brand = product[position]
        return if (brand is String) {
            INDEX
        } else {
            BRAND
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)

        return if (viewType == BRAND) {
            val cellForRow = layoutInflater.inflate(R.layout.product_row, parent, false)
            VehicleViewHolder(cellForRow)
        } else {
            val cellForRow = layoutInflater.inflate(R.layout.header_layout, parent, false)
            VehicleViewHolder(cellForRow)
        }
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val product = product[position]
        var description: String = ""

        if(product is String){
            // Set layout values
            holder.view.header_id.text = product
            holder.product = null

        } else {
            // Set layout values
            val objectProduct = product as CProduct

            if (objectProduct.description.length > 60) {
                description = objectProduct.description.substring(0, 130)
            }

            holder.view.textView_name.text = objectProduct.name
            holder.view.textView_engine.text = description.plus(" ...")
            holder.product = objectProduct

            val thumbnailImageView = holder.view.imageView_image
            Picasso.with(holder.view.context).load(objectProduct.image).into(thumbnailImageView)
        }
    }
}

class VehicleViewHolder(val view: View, var product: CProduct? = null) : RecyclerView.ViewHolder(view) {
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

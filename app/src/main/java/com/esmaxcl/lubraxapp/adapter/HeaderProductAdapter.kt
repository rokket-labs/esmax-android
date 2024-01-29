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
import kotlinx.android.synthetic.main.video_row.view.*


class HeaderProductAdapter(private val product: List<CProduct>) : RecyclerView.Adapter<CustomViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return product.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product = product[position]

        holder.view.textView_name.text = product.name
        holder.view.textView_engine.text = product.description.substring(0, 100)
        holder.product = product

        val thumbnailImageView = holder.view.imageView_image
        Picasso.with(holder.view.context).load(product.image).into(thumbnailImageView)
    }

}

class CustomViewHolder(val view: View, var product: CProduct? = null) : RecyclerView.ViewHolder(view){
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

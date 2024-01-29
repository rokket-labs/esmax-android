package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.activity.ProductDetailActivity
import com.esmaxcl.lubraxapp.model.CProduct
import kotlinx.android.synthetic.main.competitor_product_row.view.*
import com.esmaxcl.lubraxapp.R

class DatasheetAdapter(val products: List<CProduct>) : RecyclerView.Adapter<DatasheetViewHolder>() {

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatasheetViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.competitor_product_row, parent, false)
        return DatasheetViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: DatasheetViewHolder, position: Int) {
        val productObject = products[position]
        holder.view.textview_name.text = productObject.name
        holder.product = productObject
    }
}

class DatasheetViewHolder(val view: View, var product: CProduct? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (this.product != null) {

                println("Intent: Product Detail")
                println(this.product!!.name)

                /* Intent: Product Detail */
                val intent = Intent(view.context, ProductDetailActivity::class.java)
                val bundle = Bundle()

                bundle.putSerializable("product", this.product)
                intent.putExtras(bundle)

                view.context.startActivity(intent)
            }
        }
    }
}
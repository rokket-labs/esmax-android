package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.SelectVehicleModelActivity
import com.esmaxcl.lubraxapp.model.VehicleBrand
import kotlinx.android.synthetic.main.faq_row.view.*
import kotlinx.android.synthetic.main.header_layout.view.*


//class VehicleBrandAdapter(private val brands: List<VehicleBrand>,
//                          private val categoryID: Int?) : RecyclerView.Adapter<VehicleBrandViewHolder>() {

class VehicleBrandAdapter(
    private val brands: MutableList<Any>,
    private val categoryID: Int?
) : RecyclerView.Adapter<VehicleBrandViewHolder>() {

    private val INDEX = 0
    private val BRAND = 1


    // numberOfItems
    override fun getItemCount(): Int {
        return brands.count()
    }

    override fun getItemViewType(position: Int): Int {
        val brand = brands[position]
        return if (brand is String) {
            INDEX
        } else {
            BRAND
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleBrandViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)

        return if (viewType == BRAND) {
            val cellForRow = layoutInflater.inflate(R.layout.faq_row, parent, false)
            VehicleBrandViewHolder(cellForRow)
        } else {
            val cellForRow = layoutInflater.inflate(R.layout.header_layout, parent, false)
            VehicleBrandViewHolder(cellForRow)
        }

    }

    override fun onBindViewHolder(holder: VehicleBrandViewHolder, position: Int) {

        val brand = brands[position]

        if (brand is String) {
            // Set layout values
            holder.view.header_id.text = brand
            holder.brand = null
            holder.categoryID = categoryID

        } else {
            // Set layout values
            val objectBrand = brand as VehicleBrand
            holder.view.textview_question.text = objectBrand.brand
            holder.brand = objectBrand
            holder.categoryID = categoryID
        }
    }
}

class VehicleBrandViewHolder(
    val view: View,
    var brand: VehicleBrand? = null,
    var categoryID: Int? = null
) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (this.brand != null) {
                println("Intent: FaqDetail")
                /* Intent: FaqActivity Activity */
                val intent = Intent(view.context, SelectVehicleModelActivity::class.java)
                intent.putExtra("manufactureID", this.brand!!.id)
                intent.putExtra("categoryID", this.categoryID)
                view.context.startActivity(intent)
            }
        }
    }
}


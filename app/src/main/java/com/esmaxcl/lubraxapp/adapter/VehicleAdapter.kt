package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.SelectVehicleBrandActivity
import com.esmaxcl.lubraxapp.model.VehicleType
import kotlinx.android.synthetic.main.company_row.view.*


class VehicleAdapter(private val vehicle: Array<VehicleType>) :
    RecyclerView.Adapter<CustomVehicleViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return vehicle.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVehicleViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.company_row, parent, false)
        return CustomVehicleViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomVehicleViewHolder, position: Int) {


        val option = vehicle[position]
        holder.view.textView_name.text = option.name
        holder.view.ImageView_icon.setImageResource(option.icon)
        holder.vehicle = option

    }
}

class CustomVehicleViewHolder(val view: View, var vehicle: VehicleType? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            println("Clicked option competitor")
            if (this.vehicle != null) {

                /* Intent: Product Detail */
                val intent = Intent(view.context, SelectVehicleBrandActivity::class.java)
                intent.putExtra("categoryID", this.vehicle!!.id)
                view.context.startActivity(intent)
            }
        }
    }
}
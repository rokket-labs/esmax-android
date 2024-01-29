package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.SelectVehicleTypeActivity
import com.esmaxcl.lubraxapp.model.VehicleModel
import kotlinx.android.synthetic.main.faq_row.view.*


class VehicleModelAdapter(private val models: List<VehicleModel>,
                          private val category: Int?,
                          private val manufacture: String?) : RecyclerView.Adapter<VehicleModelViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return models.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleModelViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.faq_row, parent, false)
        return VehicleModelViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: VehicleModelViewHolder, position: Int) {
        // Select Current FaqActivity
        val model: VehicleModel = models[position]
        // Set layout values
        holder.view.textview_question.text = model.vehicle_model
        // Set FaqActivity Object
        holder.model = model
        holder.category = category
        holder.manufacture = manufacture
    }
}

class VehicleModelViewHolder(val view: View,
                             var model: VehicleModel? = null,
                             var category: Int? = null,
                             var manufacture: String? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (this.model != null) {
                println("Intent")
                /* Intent:  Activity */
                println(model!!.id)
                val intent = Intent(view.context, SelectVehicleTypeActivity::class.java)
                intent.putExtra("categoryID", this.category)
                intent.putExtra("manufactureID", this.manufacture)
                intent.putExtra("modelID", this.model!!.id)
                view.context.startActivity(intent)
            }
        }
    }
}
package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.SelectVehicleProductActivity
import com.esmaxcl.lubraxapp.model.VehicleModelType
import kotlinx.android.synthetic.main.faq_row.view.*


class VehicleTypeAdapter(
    private val types: List<VehicleModelType>,
    private val category: Int?,
    private val manufacture: String?,
    private val model: String?
) : RecyclerView.Adapter<VehicleTypeViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return types.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleTypeViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.faq_row, parent, false)
        return VehicleTypeViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: VehicleTypeViewHolder, position: Int) {
        // Select Current FaqActivity
        val typeObject: VehicleModelType = types[position]
        // Set layout values
        holder.view.textview_question.text = typeObject.type
        // Set FaqActivity Object
        holder.type = typeObject
        holder.category = category
        holder.manufacture = manufacture
        holder.model = model
    }
}

class VehicleTypeViewHolder(
    val view: View,
    var type: VehicleModelType? = null,
    var category: Int? = null,
    var manufacture: String? = null,
    var model: String? = null
) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (this.type != null) {
                println("Intent: FaqDetail")
                /* Intent: SelectVehicleTypeActivity Activity */
                val intent = Intent(view.context, SelectVehicleProductActivity::class.java)
                intent.putExtra("categoryID", this.category)
                intent.putExtra("manufactureID", this.manufacture)
                intent.putExtra("modelID", this.model)
                intent.putExtra("typeID", this.type!!.id)
                view.context.startActivity(intent)
            }
        }
    }
}
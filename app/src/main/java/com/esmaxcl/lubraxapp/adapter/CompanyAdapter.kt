package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.EquivalenceProduct
import com.esmaxcl.lubraxapp.model.Company
import kotlinx.android.synthetic.main.company_row.view.*


class CompanyAdapter(private val competitors: Array<Company>) :
    RecyclerView.Adapter<CustomEquivalenceTableViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return competitors.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomEquivalenceTableViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.company_row, parent, false)
        return CustomEquivalenceTableViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomEquivalenceTableViewHolder, position: Int) {

        val option = competitors[position]
        holder.view.textView_name.text = option.name
        holder.view.ImageView_icon.setImageResource(option.icon)
        holder.competitor = option
    }

}

class CustomEquivalenceTableViewHolder(val view: View, var competitor: Company? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            println("Clicked option competitor")
            if (this.competitor != null) {

                val intent = Intent(view.context, EquivalenceProduct::class.java)
                intent.putExtra("competitorID", this.competitor!!.id)
                view.context.startActivity(intent)
            }

        }
    }

}
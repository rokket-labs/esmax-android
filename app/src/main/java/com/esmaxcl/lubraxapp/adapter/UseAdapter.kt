package com.esmaxcl.lubraxapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.model.*
import com.esmaxcl.lubraxapp.R
import kotlinx.android.synthetic.main.circle_row.view.*

class UseAdapter(private val use: Array<Uses>) : RecyclerView.Adapter<UseViewHolder>() {

    override fun getItemCount(): Int {
        println("TOTAL USES: " + use.count())
        return use.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.circle_row, parent, false)
        return UseViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: UseViewHolder, position: Int) {
        val format = use[position]
        holder?.view?.ImageView_format?.setImageResource(R.drawable.format1)
        holder?.view?.textView_name?.text = format.name
        println(format.name)

    }

}


class UseViewHolder(val view: View) : RecyclerView.ViewHolder(view)


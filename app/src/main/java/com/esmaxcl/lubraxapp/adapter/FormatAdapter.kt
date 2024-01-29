package com.esmaxcl.lubraxapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.model.*
import com.esmaxcl.lubraxapp.R
import kotlinx.android.synthetic.main.circle_row.view.*


class FormatAdapter(private val format: Array<Formats>) : RecyclerView.Adapter<FormatViewHolder>() {


    override fun getItemCount(): Int {
        println("TOTAL FORMATS: " + format.count())
        return format.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormatViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.circle_row, parent, false)
        return FormatViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: FormatViewHolder, position: Int) {
        val format = format[position]
        holder?.view?.ImageView_format?.setImageResource(R.drawable.format1)
        holder?.view?.textView_name?.text = format.name
        println(format.name)

    }

}


class FormatViewHolder(val view: View) : RecyclerView.ViewHolder(view)
package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.menu_row.view.*
import com.esmaxcl.lubraxapp.model.MenuOptions
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.DatasheetActivity
import com.esmaxcl.lubraxapp.activity.EquivalenceTableActivity
import com.esmaxcl.lubraxapp.activity.FaqActivity
import com.esmaxcl.lubraxapp.activity.SelectVehicleActivity


class MenuAdapter(private val menuOptions: Array<MenuOptions>) : RecyclerView.Adapter<CustomMenuViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return menuOptions.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomMenuViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.menu_row, parent, false)
        return CustomMenuViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomMenuViewHolder, position: Int) {
        val option = menuOptions[position]
        holder?.view?.textView_name?.text = option.option
        holder?.view?.textView_subtext?.text = option.subtext
        holder?.view?.ImageView_icon?.setImageResource(option.icon)
        holder?.menuOption = option
    }

}

class CustomMenuViewHolder(
    val view: View,
    var menuOption: MenuOptions? = null
) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            println("Clicked option menu")
            if (this.menuOption != null) {

                /* Intent: Datasheet */
                if (this.menuOption!!.id == 1) {
                    val intent = Intent(view.context, SelectVehicleActivity::class.java)
                    view.context.startActivity(intent)
                }

                /* Intent: Datasheet */
                if (this.menuOption!!.id == 2) {
                    val intent = Intent(view.context, DatasheetActivity::class.java)
                    view.context.startActivity(intent)
                }

                /* Intent: Equivalence Table */
                if (this.menuOption!!.id == 3) {
                    val intent = Intent(view.context, EquivalenceTableActivity::class.java)
                    view.context.startActivity(intent)
                }

                /* Intent: FaqActivity Activity */
                if (this.menuOption!!.id == 4) {
                    val intent = Intent(view.context, FaqActivity::class.java)
                    view.context.startActivity(intent)
                }


                /* Intent: Search Lubricant Activity */
            }

        }
    }
}
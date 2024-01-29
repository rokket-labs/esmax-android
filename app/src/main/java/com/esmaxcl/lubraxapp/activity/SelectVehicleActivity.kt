package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.R.string.tipo_vehiculo
import com.esmaxcl.lubraxapp.adapter.VehicleAdapter
import com.esmaxcl.lubraxapp.model.VehicleType
import kotlinx.android.synthetic.main.activity_select_vehicle.*

class SelectVehicleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_vehicle)

        recyclerView_vehicles.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView_vehicles.layoutManager = layoutManager


        // a
        this.title = getString(R.string.tipo_vehiculo)
        showVehicles()

    }

    private fun showVehicles() {

        val vehicles: Array<VehicleType> = arrayOf(

            VehicleType(1, R.drawable.auto, "Autos"),
            VehicleType(2, R.drawable.van, "Vans"),
            VehicleType(4, R.drawable.truck, "Camiones"),
            VehicleType(5, R.drawable.largetruck, "Industrial")
        )

        runOnUiThread {
            recyclerView_vehicles.adapter = VehicleAdapter(vehicles)
        }
    }
}

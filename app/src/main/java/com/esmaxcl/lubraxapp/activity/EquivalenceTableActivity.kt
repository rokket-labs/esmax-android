package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.adapter.CompanyAdapter
import com.esmaxcl.lubraxapp.model.Company
import kotlinx.android.synthetic.main.activity_equivalence_table.*
import android.support.v7.widget.GridLayoutManager

class EquivalenceTableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equivalence_table)

        this.title = this.getString(R.string.competidores)

        recyclerView_companies.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView_companies.layoutManager = layoutManager

        showCompanies()
    }

    private fun showCompanies() {

        val companies: Array<Company> = arrayOf(
            Company(2, R.drawable.mobil_ios,  this.getString(R.string.marca_mobil)),
            Company(8, R.drawable.shell_ios, this.getString(R.string.marca_shell)),
            Company(3, R.drawable.valvoline_ios, this.getString(R.string.marca_valvoline)),
            Company(4, R.drawable.castrol_ios, this.getString(R.string.marca_castrol)),
            Company(5, R.drawable.ypf_ios, this.getString(R.string.marca_ypf)),
            Company(6, R.drawable.texaco_ios, this.getString(R.string.marca_texaco)),
            Company(7, R.drawable.total_ios, this.getString(R.string.marca_total))
        )

        runOnUiThread {
            recyclerView_companies.adapter = CompanyAdapter(companies)
        }
    }
}

package com.esmaxcl.lubraxapp.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esmaxcl.lubraxapp.model.Faq
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.activity.FaqDetail
import kotlinx.android.synthetic.main.faq_row.view.*


class FaqAdapter(private val faqs: List<Faq>) : RecyclerView.Adapter<CustomFaqViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return faqs.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomFaqViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.faq_row, parent, false)
        return CustomFaqViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomFaqViewHolder, position: Int) {
        // Select Current FaqActivity
        val faq: Faq = faqs[position]
        // Set layout values
        holder.view.textview_question.text = faq.question
        // Set FaqActivity Object
        holder.faq = faq
    }
}

class CustomFaqViewHolder(val view: View, var faq: Faq? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (this.faq != null) {
                println("Intent: FaqDetail")
                /* Intent: FaqActivity Activity */
                val intent = Intent(view.context, FaqDetail::class.java)
                intent.putExtra("question", this.faq!!.question)
                intent.putExtra("answer", this.faq!!.answer)
                view.context.startActivity(intent)
            }
        }
    }
}
package com.esmaxcl.lubraxapp.activity
import com.esmaxcl.lubraxapp.R
import com.esmaxcl.lubraxapp.model.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.esmaxcl.lubraxapp.adapter.FaqAdapter
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import kotlinx.android.synthetic.main.activity_faq.*


class FaqActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        this.title = getString(R.string.pregunta_frecuente)
        recyclerView_faqs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_faqs.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        fetchFaqs()
    }

    private fun fetchFaqs() {

        animation_view.visibility = View.VISIBLE
        println("Attempting to Fetch JSON")

        val url = "$BASE_URL/webservice/faq"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val faqFeed = gson.fromJson(body, FaqFeed::class.java)
                animation_view.visibility = View.INVISIBLE

                if (faqFeed.status) {
                    runOnUiThread {
                        println(body)
                        recyclerView_faqs.adapter = FaqAdapter(faqFeed.faq)
                    }
                } else {
                    println("Failed Request : $url")
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
                animation_view.visibility = View.INVISIBLE
            }
        })
    }
}

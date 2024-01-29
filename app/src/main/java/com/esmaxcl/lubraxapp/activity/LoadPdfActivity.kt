package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.esmaxcl.lubraxapp.R
import kotlinx.android.synthetic.main.activity_load_pdf.*

class LoadPdfActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_pdf)

        this.title = getString(R.string.ficha_tecnica)

        val intent = this.intent
        val bundle = intent.extras

        if (bundle != null) {

            val url = bundle.getSerializable("urlPdf") as String
            println(url)
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show()
            webview.settings.useWideViewPort = true
            webview.settings.javaScriptEnabled = true
            webview.loadUrl("https://google.com")
        }
    }
}

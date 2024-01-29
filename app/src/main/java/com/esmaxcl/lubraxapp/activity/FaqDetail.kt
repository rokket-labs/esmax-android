package com.esmaxcl.lubraxapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.esmaxcl.lubraxapp.R
import kotlinx.android.synthetic.main.activity_faq_detail.*


class FaqDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq_detail)

        val intent = intent
        val b = intent.extras

        if (b != null) {

            val question = b.get("question") as String
            val answer = b.get("answer") as String
            textView_question.text = question
            textView_answer.text = answer
            this.title = question
        }
    }
}

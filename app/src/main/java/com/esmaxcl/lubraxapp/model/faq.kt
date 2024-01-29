package com.esmaxcl.lubraxapp.model

class FaqFeed(
    val faq: List<Faq>,
    val status: Boolean
)

class Faq(
    val id: Int,
    val question: String,
    val answer: String
)

package com.esmaxcl.lubraxapp.model

import java.io.Serializable

class ProductResponse(
    val product: List<CProduct>,
    val status: Boolean
)

class ProductObject(
    val unique_key: String,
    val name: String,
    val engine: Engine,
    val description: String,
    val formats: Array<Formats>,
    val uses: Array<Uses>,
    val image: String,
    val pdf: String
): Serializable

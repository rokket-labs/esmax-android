package com.esmaxcl.lubraxapp.model

import java.io.Serializable

class ParentModel (
    val index: String = "",
    val children: List<VehicleBrand>
)


// Favorite Products Object Class

class ProductFeed(
    val product: List<CProduct>,
    val status: Boolean
): Serializable

class Product (
    val id: Int,
    val name: String,
    val description: String,
    val image: String
) : Serializable

// This Products Model Belong to Competitor Listing

class CompetitorProductFeed(
    val company_product: List<ProductList>,
    val status: Boolean
)
//
class ProductList(
    val id: Int,
    val unique_key: String,
    val name: String,
    val product: CProduct
) : Serializable

class CProduct(
    val unique_key: String,
    val name: String,
    val engine: Engine,
    val description: String,
    val formats: Array<Formats>,
    val uses: Array<Uses>,
    val image: String,
    val pdf: String
) : Serializable



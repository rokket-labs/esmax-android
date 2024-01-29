package com.esmaxcl.lubraxapp.model

import java.io.Serializable

class VehicleType (
    val id: Int,
    val icon: Int,
    val name: String
): Serializable

// Select Brand
class VehicleBrandResponse (
    val status: Boolean,
    val description: String,
    val manufacture: List<VehicleBrand>
): Serializable

class VehicleBrand (
    val id: String,
    val brand: String
): Serializable

// Select Model
class VehicleModelResponse (
    val status: Boolean,
    val description: String,
    val model: List<VehicleModel>
): Serializable

class VehicleModel (
    val id: String,
    val vehicle_model: String
): Serializable

// Select Type
class VehicleTypeResponse (
    val status: Boolean,
    val description: String,
    val type: List<VehicleModelType>
): Serializable

class VehicleModelType (
    val id: String,
    val type: String
): Serializable

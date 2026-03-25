package com.grocery.app.utils

import android.location.Location

object DeliveryUtils {
    // Shop location coordinates (Replace with actual shop coordinates)
    private const val SHOP_LAT = 28.7041
    private const val SHOP_LNG = 77.1025

    fun calculateDistance(customerLat: Double, customerLng: Double): Double {
        val results = FloatArray(1)
        Location.distanceBetween(SHOP_LAT, SHOP_LNG, customerLat, customerLng, results)
        return results[0] / 1000.0 // Convert meters to kilometers
    }

    fun getDeliveryCharge(distanceKm: Double): Double {
        return when {
            distanceKm <= 5.0 -> 20.0
            distanceKm <= 10.0 -> 40.0
            distanceKm <= 20.0 -> 80.0
            distanceKm <= 50.0 -> 150.0
            else -> -1.0 // Not serviceable
        }
    }
}

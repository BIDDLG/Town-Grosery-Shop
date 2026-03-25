package com.grocery.app.utils;

import android.location.Location;

public class DeliveryUtils {
    // Shop location coordinates (Replace with actual shop coordinates)
    private static final double SHOP_LAT = 28.7041;
    private static final double SHOP_LNG = 77.1025;

    public static double calculateDistance(double customerLat, double customerLng) {
        float[] results = new float[1];
        Location.distanceBetween(SHOP_LAT, SHOP_LNG, customerLat, customerLng, results);
        return results[0] / 1000.0; // Convert meters to kilometers
    }

    public static double getDeliveryCharge(double distanceKm) {
        if (distanceKm <= 5.0) return 20.0;
        if (distanceKm <= 10.0) return 40.0;
        if (distanceKm <= 20.0) return 80.0;
        if (distanceKm <= 50.0) return 150.0;
        return -1.0; // Not serviceable
    }
}

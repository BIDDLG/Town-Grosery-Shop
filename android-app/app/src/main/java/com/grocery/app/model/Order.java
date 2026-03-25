package com.grocery.app.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id = "";
    private String userId = "";
    private String customerName = "";
    private String phone = "";
    private String address = "";
    private String landmark = "";
    private String note = "";
    private double latitude = 0.0;
    private double longitude = 0.0;
    private List<OrderItem> items = new ArrayList<>();
    private double subtotal = 0.0;
    private double deliveryCharge = 0.0;
    private double total = 0.0;
    private double distanceKm = 0.0;
    private String status = "PENDING"; // PENDING, CONFIRMED, DELIVERED, CANCELLED
    private long timestamp = System.currentTimeMillis();
    private String paymentMethod = "COD";

    public Order() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public double getDeliveryCharge() { return deliveryCharge; }
    public void setDeliveryCharge(double deliveryCharge) { this.deliveryCharge = deliveryCharge; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

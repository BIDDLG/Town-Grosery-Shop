package com.grocery.app.model;

public class User {
    private String id = "";
    private String name = "";
    private String phone = "";
    private String address = "";
    private String landmark = "";
    private double latitude = 0.0;
    private double longitude = 0.0;

    public User() {}

    public User(String id, String name, String phone, String address, String landmark, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.landmark = landmark;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}

package com.grocery.app.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id = "";
    private String name = "";
    private String description = "";
    private double price = 0.0;
    private String imageUrl = "";
    private String category = "";
    private String unit = "1 kg";
    private boolean inStock = true;
    private boolean isFeatured = false;

    public Product() {}

    public Product(String id, String name, String description, double price, String imageUrl, String category, String unit, boolean inStock, boolean isFeatured) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.unit = unit;
        this.inStock = inStock;
        this.isFeatured = isFeatured;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
}

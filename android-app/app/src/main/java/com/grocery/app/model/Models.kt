package com.grocery.app.model

data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val category: String = "",
    val unit: String = "1 kg",
    val inStock: Boolean = true,
    val isFeatured: Boolean = false
)

data class Category(
    val id: String = "",
    val name: String = "",
    val iconUrl: String = ""
)

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)

data class User(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val landmark: String = ""
)

data class Order(
    val id: String = "",
    val userId: String = "",
    val customerName: String = "",
    val phone: String = "",
    val address: String = "",
    val landmark: String = "",
    val note: String = "",
    val items: List<OrderItem> = emptyList(),
    val subtotal: Double = 0.0,
    val deliveryCharge: Double = 0.0,
    val total: Double = 0.0,
    val distanceKm: Double = 0.0,
    val status: String = "PENDING", // PENDING, CONFIRMED, DELIVERED, CANCELLED
    val timestamp: Long = System.currentTimeMillis(),
    val paymentMethod: String = "COD"
)

data class OrderItem(
    val productId: String = "",
    val productName: String = "",
    val price: Double = 0.0,
    val quantity: Int = 1,
    val imageUrl: String = ""
)

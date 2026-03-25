package com.grocery.app.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.grocery.app.model.*
import kotlinx.coroutines.tasks.await
import java.util.UUID

class GroceryRepository(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")
    private val categoriesCollection = db.collection("categories")
    private val ordersCollection = db.collection("orders")
    private val usersCollection = db.collection("users")

    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = productsCollection.get().await()
            snapshot.toObjects(Product::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCategories(): List<Category> {
        return try {
            val snapshot = categoriesCollection.get().await()
            snapshot.toObjects(Category::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun placeOrder(order: Order): Boolean {
        return try {
            val orderId = UUID.randomUUID().toString()
            val newOrder = order.copy(id = orderId)
            ordersCollection.document(orderId).set(newOrder).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun saveUserDetails(user: User) {
        try {
            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            prefs.edit().apply {
                putString("id", user.id)
                putString("name", user.name)
                putString("phone", user.phone)
                putString("address", user.address)
                putString("landmark", user.landmark)
                putString("latitude", user.latitude.toString())
                putString("longitude", user.longitude.toString())
                apply()
            }
            // Also save to Firestore if user ID exists
            if (user.id.isNotEmpty()) {
                usersCollection.document(user.id).set(user).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getUserDetails(): User? {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("name", null)
        if (name.isNullOrEmpty()) return null
        
        return User(
            id = prefs.getString("id", UUID.randomUUID().toString()) ?: "",
            name = name,
            phone = prefs.getString("phone", "") ?: "",
            address = prefs.getString("address", "") ?: "",
            landmark = prefs.getString("landmark", "") ?: "",
            latitude = prefs.getString("latitude", "0.0")?.toDoubleOrNull() ?: 0.0,
            longitude = prefs.getString("longitude", "0.0")?.toDoubleOrNull() ?: 0.0
        )
    }
    
    // Admin functions
    suspend fun getAllOrders(): List<Order> {
        return try {
            val snapshot = ordersCollection.orderBy("timestamp", Query.Direction.DESCENDING).get().await()
            snapshot.toObjects(Order::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateOrderStatus(orderId: String, status: String): Boolean {
        return try {
            ordersCollection.document(orderId).update("status", status).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

package com.grocery.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.app.model.*
import com.grocery.app.repository.GroceryRepository
import com.grocery.app.utils.DeliveryUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GroceryRepository(application)

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _deliveryCharge = MutableStateFlow(0.0)
    val deliveryCharge: StateFlow<Double> = _deliveryCharge.asStateFlow()

    private val _distance = MutableStateFlow(0.0)
    val distance: StateFlow<Double> = _distance.asStateFlow()

    private val _orderPlaced = MutableStateFlow(false)
    val orderPlaced: StateFlow<Boolean> = _orderPlaced.asStateFlow()

    init {
        loadData()
        _user.value = repository.getUserDetails()
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _products.value = repository.getProducts()
            _categories.value = repository.getCategories()
            _isLoading.value = false
        }
    }

    fun addToCart(product: Product) {
        val currentCart = _cart.value.toMutableList()
        val existingItem = currentCart.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentCart.add(CartItem(product, 1))
        }
        _cart.value = currentCart
    }

    fun removeFromCart(product: Product) {
        val currentCart = _cart.value.toMutableList()
        val existingItem = currentCart.find { it.product.id == product.id }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity--
            } else {
                currentCart.remove(existingItem)
            }
        }
        _cart.value = currentCart
    }

    fun getCartTotal(): Double {
        return _cart.value.sumOf { it.product.price * it.quantity }
    }

    fun calculateDeliveryCharge(customerLat: Double, customerLng: Double) {
        val dist = DeliveryUtils.calculateDistance(customerLat, customerLng)
        _distance.value = dist
        _deliveryCharge.value = DeliveryUtils.getDeliveryCharge(dist)
    }

    fun placeOrder(name: String, phone: String, address: String, landmark: String, note: String) {
        viewModelScope.launch {
            _isLoading.value = true
            
            val currentUser = User(
                id = _user.value?.id ?: java.util.UUID.randomUUID().toString(),
                name = name,
                phone = phone,
                address = address,
                landmark = landmark
            )
            
            repository.saveUserDetails(currentUser)
            _user.value = currentUser

            val orderItems = _cart.value.map {
                OrderItem(
                    productId = it.product.id,
                    productName = it.product.name,
                    price = it.product.price,
                    quantity = it.quantity,
                    imageUrl = it.product.imageUrl
                )
            }

            val subtotal = getCartTotal()
            val total = subtotal + _deliveryCharge.value

            val order = Order(
                userId = currentUser.id,
                customerName = name,
                phone = phone,
                address = address,
                landmark = landmark,
                note = note,
                items = orderItems,
                subtotal = subtotal,
                deliveryCharge = _deliveryCharge.value,
                total = total,
                distanceKm = _distance.value
            )

            val success = repository.placeOrder(order)
            if (success) {
                _cart.value = emptyList()
                _orderPlaced.value = true
            }
            _isLoading.value = false
        }
    }

    fun resetOrderState() {
        _orderPlaced.value = false
    }
}

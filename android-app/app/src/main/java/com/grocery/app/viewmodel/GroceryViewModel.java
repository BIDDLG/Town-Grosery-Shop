package com.grocery.app.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.grocery.app.model.*;
import com.grocery.app.repository.GroceryRepository;
import com.grocery.app.utils.DeliveryUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroceryViewModel extends AndroidViewModel {
    private final GroceryRepository repository;

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<Product>> products = _products;

    private final MutableLiveData<List<Category>> _categories = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<Category>> categories = _categories;

    private final MutableLiveData<List<CartItem>> _cart = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<CartItem>> cartItems = _cart;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<User> _userDetails = new MutableLiveData<>(null);
    public final LiveData<User> userDetails = _userDetails;

    private final MutableLiveData<Double> _cartTotal = new MutableLiveData<>(0.0);
    public final LiveData<Double> cartTotal = _cartTotal;

    private final MutableLiveData<Double> _deliveryCharge = new MutableLiveData<>(0.0);
    public final LiveData<Double> deliveryCharge = _deliveryCharge;

    private final MutableLiveData<Double> _distance = new MutableLiveData<>(0.0);
    public final LiveData<Double> distance = _distance;

    private final MutableLiveData<Boolean> _orderPlaced = new MutableLiveData<>(false);
    public final LiveData<Boolean> orderPlaced = _orderPlaced;

    public GroceryViewModel(@NonNull Application application) {
        super(application);
        repository = new GroceryRepository(application);
        loadData();
        _userDetails.setValue(repository.getUserDetails());
    }

    public void loadData() {
        _isLoading.setValue(true);
        repository.getProducts().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                _products.setValue(task.getResult());
            }
            checkLoadingFinished();
        });
        repository.getCategories().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                _categories.setValue(task.getResult());
            }
            checkLoadingFinished();
        });
    }

    private void checkLoadingFinished() {
        // Simple check, could be more robust
        _isLoading.setValue(false);
    }

    public void addToCart(Product product) {
        List<CartItem> currentCart = new ArrayList<>(_cart.getValue());
        CartItem existingItem = null;
        for (CartItem item : currentCart) {
            if (item.getProduct().getId().equals(product.getId())) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            currentCart.add(new CartItem(product, 1));
        }
        _cart.setValue(currentCart);
        updateCartTotal();
    }

    public void removeFromCart(Product product) {
        List<CartItem> currentCart = new ArrayList<>(_cart.getValue());
        CartItem existingItem = null;
        for (CartItem item : currentCart) {
            if (item.getProduct().getId().equals(product.getId())) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            if (existingItem.getQuantity() > 1) {
                existingItem.setQuantity(existingItem.getQuantity() - 1);
            } else {
                currentCart.remove(existingItem);
            }
        }
        _cart.setValue(currentCart);
        updateCartTotal();
    }

    private void updateCartTotal() {
        double total = 0;
        List<CartItem> currentCart = _cart.getValue();
        if (currentCart != null) {
            for (CartItem item : currentCart) {
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        _cartTotal.setValue(total);
    }

    public double getCartTotal() {
        double total = 0;
        List<CartItem> currentCart = _cart.getValue();
        if (currentCart != null) {
            for (CartItem item : currentCart) {
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    public void calculateDeliveryCharge(double customerLat, double customerLng) {
        double dist = DeliveryUtils.calculateDistance(customerLat, customerLng);
        _distance.setValue(dist);
        _deliveryCharge.setValue(DeliveryUtils.getDeliveryCharge(dist));
    }

    public void saveUserDetails(User user) {
        repository.saveUserDetails(user);
        _userDetails.setValue(user);
    }

    public void placeOrder() {
        User user = _userDetails.getValue();
        if (user != null) {
            placeOrder(user.getName(), user.getPhone(), user.getAddress(), user.getLandmark(), "", user.getLatitude(), user.getLongitude());
        }
    }

    public void placeOrder(String name, String phone, String address, String landmark, String note, double lat, double lng) {
        _isLoading.setValue(true);

        User currentUser = new User();
        currentUser.setId(_userDetails.getValue() != null ? _userDetails.getValue().getId() : UUID.randomUUID().toString());
        currentUser.setName(name);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        currentUser.setLandmark(landmark);
        currentUser.setLatitude(lat);
        currentUser.setLongitude(lng);

        repository.saveUserDetails(currentUser);
        _userDetails.setValue(currentUser);

        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> currentCart = _cart.getValue();
        if (currentCart != null) {
            for (CartItem item : currentCart) {
                orderItems.add(new OrderItem(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getProduct().getImageUrl()
                ));
            }
        }

        double subtotal = getCartTotal();
        double total = subtotal + (_deliveryCharge.getValue() != null ? _deliveryCharge.getValue() : 0.0);

        Order order = new Order();
        order.setUserId(currentUser.getId());
        order.setCustomerName(name);
        order.setPhone(phone);
        order.setAddress(address);
        order.setLandmark(landmark);
        order.setNote(note);
        order.setLatitude(lat);
        order.setLongitude(lng);
        order.setItems(orderItems);
        order.setSubtotal(subtotal);
        order.setDeliveryCharge(_deliveryCharge.getValue() != null ? _deliveryCharge.getValue() : 0.0);
        order.setTotal(total);
        order.setDistanceKm(_distance.getValue() != null ? _distance.getValue() : 0.0);

        repository.placeOrder(order).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult()) {
                _cart.setValue(new ArrayList<>());
                _orderPlaced.setValue(true);
            }
            _isLoading.setValue(false);
        });
    }

    public void resetOrderState() {
        _orderPlaced.setValue(false);
    }
}

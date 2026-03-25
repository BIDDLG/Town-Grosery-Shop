package com.grocery.app.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.grocery.app.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroceryRepository {
    private final Context context;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference productsCollection = db.collection("products");
    private final CollectionReference categoriesCollection = db.collection("categories");
    private final CollectionReference ordersCollection = db.collection("orders");
    private final CollectionReference usersCollection = db.collection("users");

    public GroceryRepository(Context context) {
        this.context = context;
    }

    public Task<List<Product>> getProducts() {
        return productsCollection.get().continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().toObjects(Product.class);
            } else {
                return new ArrayList<>();
            }
        });
    }

    public Task<List<Category>> getCategories() {
        return categoriesCollection.get().continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().toObjects(Category.class);
            } else {
                return new ArrayList<>();
            }
        });
    }

    public Task<Boolean> placeOrder(Order order) {
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);
        return ordersCollection.document(orderId).set(order).continueWith(task -> task.isSuccessful());
    }

    public void saveUserDetails(User user) {
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("id", user.getId())
                .putString("name", user.getName())
                .putString("phone", user.getPhone())
                .putString("address", user.getAddress())
                .putString("landmark", user.getLandmark())
                .putString("latitude", String.valueOf(user.getLatitude()))
                .putString("longitude", String.valueOf(user.getLongitude()))
                .apply();

        if (user.getId() != null && !user.getId().isEmpty()) {
            usersCollection.document(user.getId()).set(user);
        }
    }

    public User getUserDetails() {
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", null);
        if (name == null || name.isEmpty()) return null;

        User user = new User();
        user.setId(prefs.getString("id", UUID.randomUUID().toString()));
        user.setName(name);
        user.setPhone(prefs.getString("phone", ""));
        user.setAddress(prefs.getString("address", ""));
        user.setLandmark(prefs.getString("landmark", ""));
        try {
            user.setLatitude(Double.parseDouble(prefs.getString("latitude", "0.0")));
            user.setLongitude(Double.parseDouble(prefs.getString("longitude", "0.0")));
        } catch (NumberFormatException e) {
            user.setLatitude(0.0);
            user.setLongitude(0.0);
        }
        return user;
    }

    public Task<List<Order>> getAllOrders() {
        return ordersCollection.orderBy("timestamp", Query.Direction.DESCENDING).get().continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().toObjects(Order.class);
            } else {
                return new ArrayList<>();
            }
        });
    }

    public Task<Boolean> updateOrderStatus(String orderId, String status) {
        return ordersCollection.document(orderId).update("status", status).continueWith(task -> task.isSuccessful());
    }
}

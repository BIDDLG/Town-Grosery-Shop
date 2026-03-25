# Fresh Grocery Android App

A complete, production-ready Android grocery delivery app for a single shop. Converted to **Java** with XML layouts for maximum compatibility.

## Features
- **Browse Products:** View categories, search items, and see detailed product info.
- **Cart & Checkout:** Add items to cart, calculate subtotal, and proceed to checkout.
- **Distance-Based Delivery:** Automatically calculates delivery charge based on the distance from the shop (max 50km).
- **Cash on Delivery (COD):** Only COD is supported.
- **One-Time Address:** Saves user details locally and in Firestore for future orders.
- **Push Notifications:** Firebase Cloud Messaging integration for order alerts.

## Tech Stack
- **Language:** Java
- **UI Toolkit:** XML Layouts (Material3)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Backend:** Firebase Firestore
- **Image Loading:** Glide
- **Navigation:** Jetpack Navigation Component

## Setup Instructions

### 1. Open in Android Studio
1. Extract the ZIP file.
2. Open Android Studio.
3. Click **Open** and select the `android-app` folder.
4. Wait for Gradle to sync.

### 2. Firebase Setup
1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Create a new project (e.g., "Fresh Grocery").
3. Add an Android app to the project with the package name `com.grocery.app`.
4. Download the `google-services.json` file.
5. Place the `google-services.json` file inside the `android-app/app/` directory.

### 3. Firestore Database Setup
1. In the Firebase Console, go to **Firestore Database** and click **Create database**.
2. Start in **Test mode** (for development) or set up proper security rules.
3. Create the following collections:
   - `products`: Add documents with fields: `id` (string), `name` (string), `description` (string), `price` (number), `imageUrl` (string), `category` (string), `unit` (string), `inStock` (boolean), `isFeatured` (boolean).
   - `categories`: Add documents with fields: `id` (string), `name` (string), `iconUrl` (string).
   - `orders`: Will be populated automatically when users place orders.
   - `users`: Will be populated automatically when users check out.

### 4. Configure Shop Location
1. Open `app/src/main/java/com/grocery/app/utils/DeliveryUtils.java`.
2. Update `SHOP_LAT` and `SHOP_LNG` with the actual coordinates of the grocery shop.

### 5. Run the App
1. Connect an Android device or start an emulator.
2. Click the **Run** button in Android Studio.

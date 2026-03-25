const functions = require("firebase-functions");
const admin = require("firebase-admin");
const nodemailer = require("nodemailer");

admin.initializeApp();

// Configure your email transport
// Use an App Password if using Gmail
const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "your-shop-email@gmail.com",
    pass: "your-app-password",
  },
});

exports.sendOrderEmail = functions.firestore
  .document("orders/{orderId}")
  .onCreate(async (snap, context) => {
    const order = snap.data();

    const mailOptions = {
      from: '"Fresh Grocery App" <your-shop-email@gmail.com>',
      to: "shop-owner@example.com", // Shop owner's email
      subject: `New Order Received - #${order.id.slice(-6).toUpperCase()}`,
      html: `
        <h2>New Order from ${order.customerName}</h2>
        <p><strong>Phone:</strong> ${order.phone}</p>
        <p><strong>Address:</strong> ${order.address}</p>
        <p><strong>Landmark:</strong> ${order.landmark}</p>
        <p><strong>Note:</strong> ${order.note}</p>
        <hr>
        <h3>Order Items:</h3>
        <ul>
          ${order.items.map(item => `<li>${item.quantity}x ${item.productName} - ₹${item.price}</li>`).join("")}
        </ul>
        <hr>
        <p><strong>Subtotal:</strong> ₹${order.subtotal}</p>
        <p><strong>Delivery Charge:</strong> ₹${order.deliveryCharge} (${order.distanceKm.toFixed(1)} km)</p>
        <h3><strong>Total:</strong> ₹${order.total} (COD)</h3>
      `,
    };

    try {
      await transporter.sendMail(mailOptions);
      console.log("Order email sent successfully");
    } catch (error) {
      console.error("Error sending email:", error);
    }
  });

/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React from 'react';
import { Download, Smartphone, CheckCircle, Package, Settings, CreditCard } from 'lucide-react';

export default function App() {
  return (
    <div className="min-h-screen bg-gray-50 text-gray-900 font-sans">
      <header className="bg-white shadow-sm border-b border-gray-200">
        <div className="max-w-5xl mx-auto px-4 py-6 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 bg-green-600 rounded-xl flex items-center justify-center text-white">
              <Smartphone size={24} />
            </div>
            <h1 className="text-2xl font-bold text-gray-900">Grocery App Android Project</h1>
          </div>
          <div className="text-sm font-medium text-gray-500">
            Kotlin • Jetpack Compose • Firebase
          </div>
        </div>
      </header>

      <main className="max-w-5xl mx-auto px-4 py-12">
        <div className="bg-white rounded-2xl shadow-sm border border-gray-200 p-8 mb-8">
          <h2 className="text-3xl font-bold mb-4">Your Android Project is Ready!</h2>
          <p className="text-lg text-gray-600 mb-8">
            I've generated a complete, production-ready Android grocery delivery app for a single shop. 
            The project is built with Kotlin, Jetpack Compose, and Firebase.
          </p>
          
          <div className="bg-blue-50 border border-blue-100 rounded-xl p-6 mb-8 flex items-start gap-4">
            <Download className="text-blue-600 shrink-0 mt-1" size={24} />
            <div>
              <h3 className="font-semibold text-blue-900 text-lg mb-1">How to download</h3>
              <p className="text-blue-800">
                Click the <strong>Export</strong> or <strong>Download ZIP</strong> button in the AI Studio settings menu (top right) 
                to download the entire workspace. The Android project is located in the <code>android-app/</code> folder.
              </p>
            </div>
          </div>

          <h3 className="text-xl font-bold mb-4">Core Features Implemented</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
            <FeatureCard icon={<Package />} title="Product Browsing" desc="Categories, search, and detailed product views with stock status." />
            <FeatureCard icon={<CreditCard />} title="COD Checkout" desc="Cash on delivery only, saves address for future use." />
            <FeatureCard icon={<Settings />} title="Admin Module" desc="Manage products, categories, orders, and delivery rules." />
            <FeatureCard icon={<CheckCircle />} title="Distance Delivery" desc="Auto-calculates delivery charge based on distance from shop." />
          </div>
        </div>

        <div className="bg-white rounded-2xl shadow-sm border border-gray-200 p-8">
          <h3 className="text-xl font-bold mb-4">Setup Instructions</h3>
          <div className="prose prose-green max-w-none">
            <ol className="list-decimal pl-5 space-y-2 text-gray-700">
              <li>Download the ZIP and extract it.</li>
              <li>Open Android Studio and select <strong>Open</strong>.</li>
              <li>Navigate to the extracted folder and select the <code>android-app</code> directory.</li>
              <li>Wait for Gradle to sync.</li>
              <li>Go to the Firebase Console, create a new project, and add an Android app with package name <code>com.grocery.app</code>.</li>
              <li>Download the <code>google-services.json</code> file and place it in <code>android-app/app/</code>.</li>
              <li>Enable <strong>Firestore Database</strong> and <strong>Storage</strong> in Firebase.</li>
              <li>Run the app on an emulator or physical device.</li>
            </ol>
          </div>
        </div>
      </main>
    </div>
  );
}

function FeatureCard({ icon, title, desc }: { icon: React.ReactNode, title: string, desc: string }) {
  return (
    <div className="border border-gray-100 bg-gray-50 rounded-xl p-4 flex gap-4">
      <div className="text-green-600 mt-1">{icon}</div>
      <div>
        <h4 className="font-semibold text-gray-900">{title}</h4>
        <p className="text-sm text-gray-600 mt-1">{desc}</p>
      </div>
    </div>
  );
}

import React from "react";
import MpesaPayment from "./components/MpesaPayment";

export default function App() {
  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center p-6">
      <div className="max-w-md w-full bg-white rounded-2xl shadow-lg p-8">
        <MpesaPayment />
      </div>
    </div>
  );
}

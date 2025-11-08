import React, { useState } from "react";
import axios from "axios";

export default function MpesaPayment() {
  const [phone, setPhone] = useState("");
  const [amount, setAmount] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const handlePay = async () => {
    if (!/^2547\d{8}$/.test(phone)) {
      setMessage("Enter a valid Safaricom number (e.g., 2547XXXXXXXX).");
      return;
    }
    if (amount <= 0) {
      setMessage("Enter a valid amount greater than 0.");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      await axios.post("http://localhost:8080/api/mpesa/stkpush", {
        phone,
        amount,
      });
      setMessage("STK Push sent! Please check your phone to complete payment.");
    } catch {
      setMessage("Error initiating payment. Try again.");
    } finally {
      setLoading(false);
    }
  };
``
  return (
    <div>
      <h2 className="text-2xl font-semibold text-center text-green-700 mb-6">
        Pay with M-Pesa
      </h2>

      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-600">Phone Number</label>
          <input
            type="text"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            placeholder="2547XXXXXXXX"
            className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500 outline-none"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-600">Amount (KES)</label>
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            placeholder="Enter amount"
            className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500 outline-none"
          />
        </div>

        <button
          onClick={handlePay}
          disabled={loading}
          className="w-full bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition disabled:opacity-50"
        >
          {loading ? "Processing..." : "Pay Now"}
        </button>

        {message && (
          <p className="text-center text-sm text-gray-700 mt-3">{message}</p>
        )}
      </div>
    </div>
  );
}

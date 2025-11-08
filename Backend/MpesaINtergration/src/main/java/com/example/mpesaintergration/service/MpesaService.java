package com.example.mpesaintergration.service;

public interface MpesaService {
    String getAccessToken();
    String stkPush(String phone, String amount);
}

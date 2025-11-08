package com.example.mpesaintergration.service;

import com.example.mpesaintergration.Configs.MpesaConfig;
import com.example.mpesaintergration.Entity.Payment;
import com.example.mpesaintergration.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MpesaServiceImpl implements MpesaService {

    private final MpesaConfig mpesaConfig;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Override
    public String getAccessToken() {
        String url = mpesaConfig.getBaseUrl() + "/oauth/v1/generate?grant_type=client_credentials";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(mpesaConfig.getConsumerKey(), mpesaConfig.getConsumerSecret());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        return (String) response.getBody().get("access_token");
    }

    @Override
    public String stkPush(String phone, String amount) {
        try {
            String accessToken = getAccessToken();
            String url = mpesaConfig.getBaseUrl() + "/mpesa/stkpush/v1/processrequest";

            Map<String, Object> payload = new HashMap<>();
            String timestamp = getTimestamp();
            String password = Base64.getEncoder().encodeToString(
                    (mpesaConfig.getShortCode() + mpesaConfig.getPassKey() + timestamp).getBytes()
            );

            payload.put("BusinessShortCode", mpesaConfig.getShortCode());
            payload.put("Password", password);
            payload.put("Timestamp", timestamp);
            payload.put("TransactionType", "CustomerPayBillOnline");
            payload.put("Amount", amount);
            payload.put("PartyA", phone);
            payload.put("PartyB", mpesaConfig.getShortCode());
            payload.put("PhoneNumber", phone);
            payload.put("CallBackURL", "https://yourdomain.com/api/mpesa/callback");
            payload.put("AccountReference", "Test123");
            payload.put("TransactionDesc", "Payment test");

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            // Save to DB
            Payment payment = new Payment();
            payment.setPhone(phone);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            paymentRepository.save(payment);

            return "STK Push sent to " + phone;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error initiating payment: " + e.getMessage();
        }
    }

    private String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    }
}

package com.example.mpesaintergration.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MpesaConfig {

    @Value("${mpesa.consumerKey}")
    private String consumerKey;

    @Value("${mpesa.consumerSecret}")
    private String consumerSecret;

    @Value("${mpesa.shortCode}")
    private String shortCode;

    @Value("${mpesa.passKey}")
    private String passKey;

    @Value("${mpesa.baseUrl}")
    private String baseUrl;

    public String getConsumerKey() { return consumerKey; }
    public String getConsumerSecret() { return consumerSecret; }
    public String getShortCode() { return shortCode; }
    public String getPassKey() { return passKey; }
    public String getBaseUrl() { return baseUrl; }
}

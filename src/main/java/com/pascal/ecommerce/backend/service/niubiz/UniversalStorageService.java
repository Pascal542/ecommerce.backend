package com.pascal.ecommerce.backend.service.niubiz;

import org.springframework.stereotype.Service;

@Service
public class UniversalStorageService {
    private String accessToken;
    private String transactionToken;
    private Double amount;

    public void storeAccessToken(String acessToken) {
        this.accessToken = acessToken;
    }
    public void storeTransactionToken(String transactionToken) {
        this.transactionToken = transactionToken;
    }
    public void storeAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccessToken() {
        if (this.accessToken == null) {
            throw new RuntimeException("Access Token no encontrado. Genera uno primero.");
        }
        return this.accessToken;
    }
    public String getTransactionToken() {
        if (this.transactionToken == null) {
            throw new RuntimeException("Transaction Token no encontrado. Genera uno primero.");
        }
        return this.transactionToken;
    }
    public Double getAmount() {
        if (this.amount == 0.0d) {
            throw new RuntimeException("Amount no encontrado. Genera uno primero.");
        }
        return this.amount;
    }
}


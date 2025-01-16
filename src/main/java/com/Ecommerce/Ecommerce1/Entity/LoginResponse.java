package com.Ecommerce.Ecommerce1.Entity;

public class LoginResponse {
    boolean success;
    private String token;

    // Constructor
    public LoginResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

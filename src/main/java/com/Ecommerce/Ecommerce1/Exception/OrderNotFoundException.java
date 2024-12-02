package com.Ecommerce.Ecommerce1.Exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}

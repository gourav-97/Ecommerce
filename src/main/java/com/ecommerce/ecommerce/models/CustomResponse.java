package com.ecommerce.ecommerce.models;

import org.springframework.http.ResponseEntity;

public class CustomResponse<T> {
    private int statusCode;
    private String message;
    private T t;

    public CustomResponse(int statusCode, String message, T t) {
        this.statusCode = statusCode;
        this.message = message;
        this.t = t;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getT() {
        return t;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setT(T t) {
        this.t = t;
    }
}

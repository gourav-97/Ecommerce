package com.ecommerce.ecommerce.models;

public class CustomResponse<ResponseData> {
    private int statusCode;
    private String message;
    private ResponseData responseData;

    public CustomResponse(int statusCode, String message, ResponseData responseData) {
        this.statusCode = statusCode;
        this.message = message;
        this.responseData = responseData;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }
}

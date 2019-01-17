package com.ECommerce.Ecommerce.Models;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String msg)
    {
        super(msg);
    }
}

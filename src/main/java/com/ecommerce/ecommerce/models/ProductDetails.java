package com.ecommerce.ecommerce.models;

public class ProductDetails {
    String productId;
    int quantity;

    public ProductDetails() {
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}

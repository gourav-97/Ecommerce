package com.ecommerce.ecommerce.models;

public class ProductDetails {
    String productId;
    int quantity;
    boolean reduce;

    public ProductDetails() {
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReduce(boolean reduce) {
        this.reduce = reduce;
    }

    public String getProductId() {
        return productId;
    }

    public boolean isReduce() {
        return reduce;
    }

    public int getQuantity() {
        return quantity;
    }


}

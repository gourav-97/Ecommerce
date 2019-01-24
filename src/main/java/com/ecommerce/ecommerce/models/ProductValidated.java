package com.ecommerce.ecommerce.models;

import javax.validation.constraints.NotNull;


public class ProductValidated {

    private String category;
    private String categoryId;
    private String productName;
    private String productId;
    private int price;
    private int quantity;

    public ProductValidated() {

    }

    public ProductValidated(String category, String categoryId, String productName, String productId, int price, int quantity) {
        this.category = category;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
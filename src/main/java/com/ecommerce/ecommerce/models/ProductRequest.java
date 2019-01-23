package com.ecommerce.ecommerce.models;

public class ProductRequest {
    private String parentId;
    private String productName;
    private String productId;
    private String brand;
    private int price;
    private String desc;
    private int quantity;
    private Object genFeatures;
    private Object prodSpecs;

    public ProductRequest() {
    }

    public ProductRequest(String parentId, String productName, String productId, String brand, int price, String desc, int quantity, Object genFeatures, Object prodSpecs) {
        this.parentId = parentId;
        this.productName = productName;
        this.productId = productId;
        this.brand = brand;
        this.price = price;
        this.desc = desc;
        this.quantity = quantity;
        this.genFeatures = genFeatures;
        this.prodSpecs = prodSpecs;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object getGenFeatures() {
        return genFeatures;
    }

    public void setGenFeatures(Object genFeatures) {
        this.genFeatures = genFeatures;
    }

    public Object getProdSpecs() {
        return prodSpecs;
    }

    public void setProdSpecs(Object prodSpecs) {
        this.prodSpecs = prodSpecs;
    }
}

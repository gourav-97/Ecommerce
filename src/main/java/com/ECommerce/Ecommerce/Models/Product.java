package com.ECommerce.Ecommerce.Models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

public class Product {

    @NotNull(message = "Cannot be NUll")
    private  String category;
    private ObjectId categoryId;
    private String subCategory;
    private ObjectId subCategoryId;
    private String productName;
    @NotNull(message = "Product Id can not be Null")
    private String productId;
    private String brand;
    private int price;
    private String desc;
    private int quantity;
    private Object genFeatures;
    private Object prodSpecs;

    public Product() {

    }

    public Product(@NotNull(message = "Cannot be NUll") String category, ObjectId categoryId, String subCategory, ObjectId subCategoryId, String productName, @NotNull(message = "Product Id can not be Null") String productId, String brand, int price, String desc, int quantity, Object genFeatures, Object prodSpecs) {
        this.category = category;
        this.categoryId = categoryId;
        this.subCategory = subCategory;
        this.subCategoryId = subCategoryId;
        this.productName = productName;
        this.productId = productId;
        this.brand = brand;
        this.price = price;
        this.desc = desc;
        this.quantity = quantity;
        this.genFeatures = genFeatures;
        this.prodSpecs = prodSpecs;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ObjectId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ObjectId categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public ObjectId getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(ObjectId subCategoryId) {
        this.subCategoryId = subCategoryId;
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

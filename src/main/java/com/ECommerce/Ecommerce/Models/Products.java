package com.ECommerce.Ecommerce.Models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document (collection = "Products")
public class Products {
    @Id
    public ObjectId _id;
    @NotNull(message = "Cannot be NUll")
    public  String category;
    public int categoryId;
    public String subCategory;
    public String productName;
    @NotNull(message = "Product Id can not be Null")
    public String productId;
    public String brand;
    public int price;
    public String desc;
    public int quantity;
    public Object genFeatures;
    public Object prodSpecs;


    public Products() {

    }
    public Products(ObjectId _id,String category, int categoryId, String subCategory, String productName, String productId, String brand, int price, String desc, int quantity, Object genFeatures, Object prodSpecs) {
        this.category = category;
        this.categoryId = categoryId;
        this.subCategory = subCategory;
        this.productName = productName;
        this.productId = productId;
        this.brand = brand;
        this.price = price;
        this.desc = desc;
        this.quantity = quantity;
        this.genFeatures = genFeatures;
        this.prodSpecs = prodSpecs;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public void setcategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setGenFeatures(Object genFeatures) {
        this.genFeatures = genFeatures;
    }

    public void setProdSpecs(Object prodSpecs) {
        this.prodSpecs = prodSpecs;
    }


    public String getCategory() {
        return category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public Object getGenFeatures() {
        return genFeatures;
    }

    public Object getProdSpecs() {
        return prodSpecs;
    }
}

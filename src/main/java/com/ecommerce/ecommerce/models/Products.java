package com.ecommerce.ecommerce.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document (collection = "Products")
public class Products {
    @Id
    private ObjectId _id;
    private String parentId;
    private String productName;
    @Indexed(unique=true)
    @NotNull(message = "Product Id can not be Null")
    private String productId;
    private String brand;
    private int price;
    private String desc;
    private int quantity;
    private Object genFeatures;
    private Object prodSpecs;
    private int popularScore;


    public Products() {

    }

    public Products(ObjectId _id, String parentId, String productName, @NotNull(message = "Product Id can not be Null") String productId, String brand, int price, String desc, int quantity, Object genFeatures, Object prodSpecs, int popularScore) {
        this._id = _id;
        this.parentId = parentId;
        this.productName = productName;
        this.productId = productId;
        this.brand = brand;
        this.price = price;
        this.desc = desc;
        this.quantity = quantity;
        this.genFeatures = genFeatures;
        this.prodSpecs = prodSpecs;
        this.popularScore = popularScore;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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

    public int getPopularScore() {
        return popularScore;
    }

    public void setPopularScore(int popularScore) {
        this.popularScore = popularScore;
    }
}

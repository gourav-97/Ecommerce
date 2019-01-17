package com.ECommerce.Ecommerce.Models;

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
    @NotNull(message = "Product Id can not be Null")
    private String productId;
    private String brand;
    private int price;
    private String desc;
    private int quantity;
    private Object genFeatures;
    private Object prodSpecs;


    public Products() {

    }

    public Products(ObjectId _id,String parentId, String productName, @NotNull(message = "Product Id can not be Null") String productId, String brand, int price, String desc, int quantity, Object genFeatures, Object prodSpecs) {
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
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}

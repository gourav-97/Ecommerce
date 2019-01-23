package com.ecommerce.ecommerce.models;

public class Cat {
    private String categoryName;
    private String categoryId;
    private String desc;
    private String picURL;
    public Cat()
            {

            }

    public Cat(String categoryName, String categoryId, String desc, String picURL) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.desc = desc;
        this.picURL = picURL;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
}
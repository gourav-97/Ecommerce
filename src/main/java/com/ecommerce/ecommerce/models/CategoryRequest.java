package com.ecommerce.ecommerce.models;

public class CategoryRequest {
    private String categoryName;
    private String parentId;
    private String desc;
    private String picURL;
    private int topScore;

    public CategoryRequest() {
    }

    public CategoryRequest(String categoryName, String parentId, String desc, String picURL, int topScore) {
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.desc = desc;
        this.picURL = picURL;
        this.topScore = topScore;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }
}

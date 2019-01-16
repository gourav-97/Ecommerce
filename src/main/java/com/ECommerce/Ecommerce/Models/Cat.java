package com.ECommerce.Ecommerce.Models;

public class Cat {
    private String categoryName;
    private String desc;
    private String picURL;

    public Cat()
            {

            }
    public Cat(String categoryName, String desc, String picURL) {
        this.categoryName = categoryName;
        this.desc = desc;
        this.picURL = picURL;
        }

    public String getCategoryName() {
            return categoryName;
            }

    public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
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
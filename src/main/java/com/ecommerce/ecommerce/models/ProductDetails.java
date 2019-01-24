package com.ecommerce.ecommerce.models;

import java.util.List;

public class ProductDetails
{
    private List<ProductValidated> productIds;
    private boolean toReduce;

    public ProductDetails() {
    }

    public ProductDetails(List<ProductValidated> productsToBeUpdated, boolean toReduce) {
        this.productIds = productsToBeUpdated;
        this.toReduce = toReduce;
    }

    public ProductDetails(List<ProductValidated> productsToBeUpdated) {
        this.productIds = productsToBeUpdated;
    }

    public List<ProductValidated> getProductIds() {
        return productIds;
    }

    public boolean isToReduce() {
        return toReduce;
    }

    public void setProductIds(List<ProductValidated> productIds) {
        this.productIds = productIds;
    }

    public void setToReduce(boolean toReduce) {
        this.toReduce = toReduce;
    }
}

package com.ecommerce.ecommerce.models;

import java.util.List;

public class ProductDetails
{
    private List<ProductValidated> productsToBeUpdated;
    private boolean toReduce;

    public ProductDetails(List<ProductValidated> productsToBeUpdated) {
        this.productsToBeUpdated = productsToBeUpdated;
    }

    public List<ProductValidated> getProductsToBeUpdated() {
        return productsToBeUpdated;
    }

    public boolean isToReduce() {
        return toReduce;
    }

    public void setProductsToBeUpdated(List<ProductValidated> productsToBeUpdated) {
        this.productsToBeUpdated = productsToBeUpdated;
    }

    public void setToReduce(boolean toReduce) {
        this.toReduce = toReduce;
    }
}

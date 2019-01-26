package com.ecommerce.ecommerce.models;

import com.ecommerce.ecommerce.exceptions.CategoryNotInsertedException;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.exceptions.ProductNotInsertedException;
import com.ecommerce.ecommerce.service.CategoryService;
import com.ecommerce.ecommerce.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validation
{
    @Autowired
    ProductsService productsService;

    @Autowired
    CategoryService categoryService;

    Validation(){}
    public String addProduct(ProductRequest productDetails) throws ProductNotInsertedException {
        if(productDetails.getParentId()==null||productDetails.getParentId().length()<12)
            return "Enter Valid parentId";
        else if(productDetails.getProductId()==null)
            return "Enter valid productId";
        else
            return productsService.addProduct(productDetails);
    }

    public String addCategory(CategoryRequest categoryDetails) throws CategoryNotInsertedException {
        if(categoryDetails.getParentId()!=null && categoryDetails.getParentId().length()<12)
            return "Enter Valid parentId";
        else
            return categoryService.addCategory(categoryDetails);
    }

    public String updateQuantity(ProductDetails productDetails) throws ProductNotFoundException
    {
        for(ProductValidated p:productDetails.getProductIds())
        {
            if(p.getProductId().equals(null))
                throw new ProductNotFoundException("enter valid productId");
        }
        return productsService.updateQuantity(productDetails);
    }

    public List<Product> filterByPopularScore(String subCategoryId, int score) throws ProductNotFoundException {
        if(score < 0 || score > 5 )
            throw new ProductNotFoundException("Enter Valid Popular Score");
        return productsService.filterByPopularScore(subCategoryId,score);

    }

//    public List<Product> displayByPopularScore(int score) throws ProductNotFoundException {
//        if(score < 0 || score > 5 )
//            throw new ProductNotFoundException("Enter Valid Popular Score");
//        return productsService.displayByPopularScore(score);
//
//    }

//    public List<Category> displayByTopScore(int topScore)
//    {
//        if(topScore < 0 || topScore > 5 )
//            throw new CategoryNotFoundException("Enter ");
//        categoryService.displayByTopScore(topScore);
//    }
}

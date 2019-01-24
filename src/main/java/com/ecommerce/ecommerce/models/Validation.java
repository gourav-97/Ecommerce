package com.ecommerce.ecommerce.models;

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

    public String updateQuantity(List<ProductDetails> productDetails) throws ProductNotFoundException
    {
        for(ProductDetails p:productDetails)
        {
            if(p.productId==null)
            {
                return "Send valid productID";
            }
        }
        return productsService.updateQuantity(productDetails);
    }

    public List<Product> filterByPopularScore(String subCategoryId, int score) throws ProductNotFoundException {
        if(score < 0 || score > 5 )
            throw new ProductNotFoundException("enter valid popular score");
        return productsService.filterByPopularScore(subCategoryId,score);

    }
}

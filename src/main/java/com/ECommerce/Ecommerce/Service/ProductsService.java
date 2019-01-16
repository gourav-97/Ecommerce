package com.ECommerce.Ecommerce.Service;

import com.ECommerce.Ecommerce.Models.Products;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    ProductsRepository productsRepository;
    ProductsRepositoryCustom productsRepositoryCustom;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, ProductsRepositoryCustom productsRepositoryCustom) {
        this.productsRepository = productsRepository;
        this.productsRepositoryCustom = productsRepositoryCustom;
    }

    public List<Products> getsubCategories(String category) {
        List<Products> subCategories = productsRepositoryCustom.getSubCategories(category);
        return  subCategories;
    }
}

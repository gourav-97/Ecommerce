package com.ECommerce.Ecommerce.Service;

import com.ECommerce.Ecommerce.Models.Category;
import com.ECommerce.Ecommerce.Models.Product;
import com.ECommerce.Ecommerce.Models.Products;
import com.ECommerce.Ecommerce.Repositories.CategoryRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;
    ProductsRepositoryCustom productsRepositoryCustom;

    public ProductsService(CategoryRepository categoryRepository, ProductsRepository productsRepository, ProductsRepositoryCustom productsRepositoryCustom) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.productsRepositoryCustom = productsRepositoryCustom;
    }

    public List<Product> getAllProduct(){
        List<Product> product = new ArrayList<>();
        List<Products> products = productsRepository.findAll();
        for(Products prod:products) {
            Category subCategory = categoryRepository.findBy_id(prod.getParentId());
            String subCategoryName = subCategory.getCategoryName();
            String subCategoryId = subCategory.get_id();
            String subCategoryParentId = subCategory.getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
            Product p = new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs());
            product.add(p);
        }
        return product;
    }
    public Product getByProductId(String productId){
        Products prod = productsRepository.findByproductId(productId);
        String subCategoryName = categoryRepository.findBy_id(prod.getParentId()).getCategoryName();
        String subCategoryId = categoryRepository.findBycategoryName(subCategoryName).get_id();
        String subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
        String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();



        String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();

        Product product = new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs());
        return product;
    }

}

package com.ECommerce.Ecommerce.Service;

import com.ECommerce.Ecommerce.Models.Category;
import com.ECommerce.Ecommerce.Models.Product;
import com.ECommerce.Ecommerce.Models.Products;
import com.ECommerce.Ecommerce.Repositories.CategoryRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepositoryCustom;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.X11.XConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class ProductsService {

    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;
    ProductsRepositoryCustom productsRepositoryCustom;

//    @Autowired
//    public ProductsService(ProductsRepository productsRepository, ProductsRepositoryCustom productsRepositoryCustom) {
//        this.productsRepository = productsRepository;

    public ProductsService(CategoryRepository categoryRepository, ProductsRepository productsRepository, ProductsRepositoryCustom productsRepositoryCustom) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.productsRepositoryCustom = productsRepositoryCustom;
    }
//        this.productsRepositoryCustom = productsRepositoryCustom;
//    }

  public List<Product> getAllProduct(){
      List<Product> product = new ArrayList<>();
      List<Products> products = productsRepository.findAll();
      for(Products prod:products) {
          String subCategoryName = categoryRepository.findBy_id(prod.getParentId()).getCategoryName();
          ObjectId subCategoryId = categoryRepository.findBycategoryName(subCategoryName).get_id();
          ObjectId subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
          String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
          ObjectId categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
          Product p = new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs());
          product.add(p);
          }
    return product;
}

}

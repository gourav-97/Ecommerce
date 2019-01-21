package com.ECommerce.Ecommerce.Service;


import com.ECommerce.Ecommerce.Models.*;
import com.ECommerce.Ecommerce.Repositories.CategoryRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    MongoTemplate mongoTemplate;
    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;
    @Autowired
    ProductsService productsService;
    public CategoryService(MongoTemplate mongoTemplate, CategoryRepository categoryRepository, ProductsRepository productsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
    }

    public List<Cat> getAllCategories() throws CategoryNotFoundException{

        List<Cat> categories = new ArrayList<>();

        List<Category> category = categoryRepository.findCategoryName();
        if(category==null)
            throw new CategoryNotFoundException("There are 0 categories in database");
        for(Category c:category)
        {
            Cat newCategory = new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL());
            categories.add(newCategory);
        }
        return categories;
    }

    public List<Cat> getSubCategories(String categoryId) throws CategoryNotFoundException{
        List<Category> categories = categoryRepository.findByparentId(categoryId);
        if(categories==null)
            throw new CategoryNotFoundException("There are 0 categories in database");

        List<Cat> subCategories = new ArrayList<>();
        for(Category c:categories)
        {
            Cat category = new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL());
            subCategories.add(category);
        }
        return subCategories;
    }

    public List<Product> getProductsInSubCat(String categoryId,String subCategoryId) throws CategoryNotFoundException,ProductNotFoundException {
        List<Product> products = new ArrayList<>();

        List<Products> productByParentId = productsRepository.findByparentId(subCategoryId);
        if(productByParentId==null)
            throw new CategoryNotFoundException("There are 0 categories in database");
        for(Products prod: productByParentId) {
            products.add(productsService.getByProductId(prod.getProductId()));
        }
        return products;
    }
}

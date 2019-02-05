package com.ecommerce.ecommerce.service;


import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.exceptions.CategoryNotInsertedException;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.CategoryRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CategoryService {

    MongoTemplate mongoTemplate;
    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;

    @Autowired
    ProductsService productsService;

    @Lazy @Autowired
    CategoryService categoryService;

    public CategoryService(MongoTemplate mongoTemplate, CategoryRepository categoryRepository, ProductsRepository productsRepository,ProductsService productsService) {
        this.mongoTemplate = mongoTemplate;
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
    }


    //to add a single category/sub-category in database
    public String addCategory(CategoryRequest categoryDetails) throws CategoryNotInsertedException {

        Category categoryObject = new Category(null,categoryDetails.getCategoryName(),categoryDetails.getParentId(),categoryDetails.getDesc(),categoryDetails.getPicURL(),categoryDetails.getTopScore());
        Category c=mongoTemplate.insert(categoryObject,"Category");
        if(c==null)
            throw new CategoryNotInsertedException("Category could not be Inserted, Try Again");
        return categoryObject.get_id();
    }


    // returns a list of top most level categories
    public List<Cat> getAllCategories() throws CategoryNotFoundException {

        List<Cat> categories = new ArrayList<>();

        List<Category> category = categoryRepository.findCategoryName();
        if(category==null)
            //throw new HttpResponseException
            //return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(404,"Ther are 0 categories in database",null));
               throw new CategoryNotFoundException("There are no Categories matching your query");
        for(Category c:category)
        {
            Cat newCategory = new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL(),c.getTopScore());
            categories.add(newCategory);
        }
        //return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(200,"ok",categories));
        return categories;
    }

    //returns a list of all sub-categories in given category
    public List<Cat> getSubCategories(String categoryId) throws CategoryNotFoundException{
        List<Category> categories = categoryRepository.findByparentId(categoryId);
        if(categories.isEmpty())
            throw new CategoryNotFoundException("There are no SubCategories for this particular category at present");
        List<Cat> subCategories = new ArrayList<>();
        for(Category c:categories)
        {
            Cat category = new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL(),c.getTopScore());
            subCategories.add(category);
        }
        return subCategories;
    }

    //returns a list of all products in given sub-category
    public List<Product> getProductsInSubCat(String subCategoryId) throws CategoryNotFoundException, ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        List<Products> productByParentId = productsRepository.findByparentId(subCategoryId);
        if(!(productByParentId.size()>0))
            throw new CategoryNotFoundException("There are No Products for sale under this category");
        for(Products prod: productByParentId) {
            System.out.println(prod.getProductId());
            Product product = productsService.getByProductId(prod.getProductId());
            if(product.getQuantity()>0)
                products.add(product);
        }
        return products;
    }

    //display on home page (max 10) top categories
    public List<Cat> displayByTopScore() throws CategoryNotFoundException {
        Query query=new Query();
        //int n=new Random().nextInt(2)+1;
        query.limit(6).skip(1);
        query.addCriteria(Criteria.where("topScore").gte(4).andOperator(Criteria.where("parentId").exists(true)));
        List<Category> requiredCategory=mongoTemplate.find(query,Category.class);

        if(requiredCategory.size()==0)
            throw new CategoryNotFoundException("There are no categories matching your filter");

        List<Cat> validCat=new ArrayList<>();
        for(Category c:requiredCategory)
        {
            validCat.add(new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL(),c.getTopScore()));
        }
        return validCat;
    }
}

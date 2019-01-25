package com.ecommerce.ecommerce.service;


import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.CategoryRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Lazy @Autowired
    CategoryService categoryService;

    public CategoryService(MongoTemplate mongoTemplate, CategoryRepository categoryRepository, ProductsRepository productsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
    }

    public String addCategory(CategoryRequest categoryDetails) throws CategoryNotInsertedException {

        Category categoryObject = new Category(null,categoryDetails.getCategoryName(),categoryDetails.getParentId(),categoryDetails.getDesc(),categoryDetails.getPicURL(),categoryDetails.getTopScore());
        Category c=mongoTemplate.insert(categoryObject,"Category");
        if(c==null)
            throw new CategoryNotInsertedException("Category is not inserted");
        return categoryObject.get_id();
    }

    public ResponseEntity<CustomResponse> getAllCategories() throws CategoryNotFoundException{

        List<Cat> categories = new ArrayList<>();

        List<Category> category = categoryRepository.findCategoryName();
        if(category==null)
            //throw new HttpResponseException
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(404,"Ther are 0 categories in database",null));
               // throw new CategoryNotFoundException("There are 0 categories in database");
        for(Category c:category)
        {
            Cat newCategory = new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL(),c.getTopScore());
            categories.add(newCategory);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(200,"ok",categories));
        //return categories;
    }

    public ResponseEntity<CustomResponse> getSubCategories(String categoryId) throws CategoryNotFoundException{
        List<Category> categories = categoryRepository.findByparentId(categoryId);
        if(categories.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(404,"Ther are 0 categories in database",null));
            //throw new CategoryNotFoundException("There are 0 categories in database");
        List<Cat> subCategories = new ArrayList<>();
        for(Category c:categories)
        {
            Cat category = new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL(),c.getTopScore());
            subCategories.add(category);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(200,"ok",subCategories));
        //return subCategories;
    }

    public List<Product> getProductsInSubCat(String subCategoryId) throws CategoryNotFoundException, ProductNotFoundException {
        List<Product> products = new ArrayList<>();

        List<Products> productByParentId = productsRepository.findByparentId(subCategoryId);
        if(productByParentId==null)
            throw new CategoryNotFoundException("There are 0 categories in database");
        for(Products prod: productByParentId) {
            products.add(productsService.getByProductId(prod.getProductId()));
        }
        return products;
    }

    public List<Cat> displayByTopScore() throws CategoryNotFoundException {
        Query query=new Query();
        query.addCriteria(Criteria.where("topScore").gte("4"));
        List<Category> requiredCategory=mongoTemplate.find(query,Category.class);

        if(requiredCategory.size()==0)
            throw new CategoryNotFoundException("there are no products matching your filter");

        List<Cat> validCat=new ArrayList<>();
        for(Category c:requiredCategory)
        {
            validCat.add(new Cat(c.getCategoryName(),c.get_id(),c.getDesc(),c.getPicURL(),c.getTopScore()));
        }
        return validCat;
    }
}

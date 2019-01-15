package com.ECommerce.Ecommerce;

import com.ECommerce.Ecommerce.Models.Products;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.query.Query;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("/")
    public String Welcome()
    {
        return "Welcome To The Site";
    }

    @RequestMapping("/categories")
    public List<Products> Getcategories() {
        Query query = new Query();
        List<Products> categories =
                mongoTemplate.findDistinct(query,"category","Products",Products.class,Products.class);
        return categories;
    }

    @RequestMapping("/categories/{category}")
    public List<Products> Getsubcategories(@PathVariable String category) {

        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        List<Products> subcategories =
                mongoTemplate.findDistinct(query,"subCategory","Products",Products.class,Products.class);
        return subcategories;
    }

    @RequestMapping("/categories/{category}/{subcategory}")
    public List<Products> Getproducts(@PathVariable String category,@PathVariable String subcategory) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subCategory").is(subcategory));
        List<Products> products = mongoTemplate.find(query,Products.class);
        return products;
    }
    
    @RequestMapping("/all")
    public List<Products> ShowAll() {
        return productsRepository.findAll();
    }

    @RequestMapping("/products/{id}")
    public List<Products> getProducts(@PathVariable String id) {
        return productsRepository.findBycategory(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products")
    public String addProduct(@RequestBody @Valid Products product){
        product.set_id(ObjectId.get());
        productsRepository.save(product);
        return product.getProductId();
    }
}

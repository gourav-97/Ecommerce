package com.ECommerce.Ecommerce.Repositories;


import com.ECommerce.Ecommerce.Models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsRepositoryCustomImpl implements ProductsRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductsRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public List<Products> getSubCategories(String category)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        List<Products> subcategories =
                mongoTemplate.findDistinct(query,"subCategory","Products",Products.class,Products.class);
        return subcategories;
    };


}

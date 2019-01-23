package com.ecommerce.ecommerce.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductsRepositoryCustomImpl implements ProductsRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

}

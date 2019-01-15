package com.ECommerce.Ecommerce;

import com.ECommerce.Ecommerce.Models.Products;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

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

//    @RequestMapping("/categories")
//    public List<Products> GetProducts() {
//        return productsRepository.findAll();
//    }
//

    @RequestMapping("/products")
    public List<Products> GetProducts() {
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

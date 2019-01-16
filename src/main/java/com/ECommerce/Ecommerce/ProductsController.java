package com.ECommerce.Ecommerce;
import com.ECommerce.Ecommerce.Models.Product;
import com.ECommerce.Ecommerce.Models.Cat;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Service.CategoryService;
import com.ECommerce.Ecommerce.Service.ProductsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ProductsService productsService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/")
    public String Welcome()
    {
        return "Welcome To The Site";
    }

    @RequestMapping("/products")
    public List<Product> ShowAll() {
        return productsService.getAllProduct();
    }

    @RequestMapping("/products/{productId}")
    public Product ShowById(@PathVariable String productId) {
        return productsService.getByProductId(productId);
    }

    @RequestMapping("/categories")
    public List<Cat> Getcategories() {
        return categoryService.getAllCategories();
    }
    @RequestMapping("/categories/{categoryId}")
    public List<Cat> Getsubcategories(@PathVariable ObjectId categoryId) {
        return categoryService.getsubCategories(categoryId);
    }

//    @RequestMapping("/product")
//    public List<Products> getProducts() {
//        return productsRepository.findAll();
//    }
//
//    @RequestMapping("/product/{productid}")
//    public List<Products> getProductsById(@PathVariable String productid) {
//        return productsRepository.findByproductId(productid);
//    }
//
//    @RequestMapping(method = RequestMethod.POST, value = "/products")
//    public String addProduct(@RequestBody @Valid Products product){
//        product.set_id(ObjectId.get());
//        productsRepository.save(product);
//        return product.getProductId();
//    }

}

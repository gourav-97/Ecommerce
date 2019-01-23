package com.ecommerce.ecommerce.controller;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.service.CategoryService;
import com.ecommerce.ecommerce.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotNull;
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

    @Autowired
    Validation validation;

    @RequestMapping(method=RequestMethod.GET,value="/")
    public String welcome(){
        return "Welcome To The Site";
    }

    @RequestMapping(method=RequestMethod.GET,value="/products")
    public List<Product> showAll() throws ProductNotFoundException {
            return productsService.getAllProduct();
    }

    @RequestMapping(method=RequestMethod.GET,value="/products/{productId}")
    public List<Product> showById (@PathVariable List<String> productId) throws ProductNotFoundException {
            return productsService.getByProductId(productId);
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories")
    public List<Cat> getCategories() throws CategoryNotFoundException {
            return categoryService.getAllCategories();
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}")
    public List<Cat> getSubCategories(@PathVariable String categoryId) throws CategoryNotFoundException {
            return categoryService.getSubCategories(categoryId);
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}/products")
    public List<Product> getProductsByCategory(@PathVariable String categoryId) throws CategoryNotFoundException {
           List<Product> products = productsService.getProductByCategory(categoryId);
           return products;
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}/{subCategoryId}")
    public List<Product> getProduct(@PathVariable @NotNull String categoryId, @PathVariable String subCategoryId) throws CategoryNotFoundException, ProductNotFoundException {
            return categoryService.getProductsInSubCat(subCategoryId);
    }

    @RequestMapping(method=RequestMethod.POST,value="/addCategory")
    public String addCategory(@RequestBody CategoryRequest categoryDetails) throws CategoryNotInsertedException {
        return validation.addCategory(categoryDetails);
    }

    @RequestMapping(method=RequestMethod.POST,value="/addProduct")
    public String addProduct(@RequestBody ProductRequest productDetails) throws ProductNotInsertedException {
        return validation.addProduct(productDetails);
    }

    @RequestMapping(method=RequestMethod.POST,value="/reduceQuantity")
    public String reduceQuantity(@RequestBody List<ProductDetails> productDetails) throws ProductNotFoundException {
        return validation.reduceQuantity(productDetails);
    }

}

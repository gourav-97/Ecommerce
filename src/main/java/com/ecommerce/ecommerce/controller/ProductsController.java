package com.ecommerce.ecommerce.controller;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.service.CategoryService;
import com.ecommerce.ecommerce.service.ProductsService;
import org.json.JSONArray;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
//import org.json.JSONObject;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @RequestMapping(method=RequestMethod.GET,value="/sortByPriceLTH/{subCategoryId}")
    public List<Product> sortByPriceLTH(@PathVariable  String subCategoryId){
        return productsService.sortByPriceLTH(subCategoryId);
    }

    @RequestMapping(method=RequestMethod.GET,value="/sortByPriceHTL/{subCategoryId}")
    public List<Product> sortByPriceHTL(@PathVariable  String subCategoryId){
        return productsService.sortByPriceHTL(subCategoryId);
    }

    @RequestMapping(method=RequestMethod.GET,value="/filterByPopularScore/{subCategoryId}/{score}")
    public List<Product> filterByPopularScore(@PathVariable("subCategoryId") String subCategoryId, @PathVariable("score") int score) throws ProductNotFoundException {
        return validation.filterByPopularScore(subCategoryId,score);
    }

    @RequestMapping(method=RequestMethod.GET,value="/displayByPopularScore")
    public List<Product> displayByPopularScore() throws ProductNotFoundException {
        return productsService.displayByPopularScore();
    }

    @RequestMapping(method=RequestMethod.GET,value="/displayByTopScore/")
    public List<Cat> displayByTopScore() throws CategoryNotFoundException {
        return categoryService.displayByTopScore();
    }

    @RequestMapping(method=RequestMethod.POST,value = "/getProductsById")
    public List<ProductValidated> getProductsById(@RequestBody List<String> productIds) throws ProductNotFoundException{
        return productsService.getByProductIds(productIds);
    }
    @RequestMapping(method=RequestMethod.POST,value="/addCategory")
    public String addCategory(@RequestBody CategoryRequest categoryDetails) throws CategoryNotInsertedException {
        return validation.addCategory(categoryDetails);
    }

    @RequestMapping(method=RequestMethod.POST,value="/addProduct")
    public String addProduct(@RequestBody ProductRequest productDetails) throws ProductNotInsertedException {
        return validation.addProduct(productDetails);
    }

    @RequestMapping(method=RequestMethod.POST,value="/check")
    public List<String> check(@RequestBody String check) throws ProductNotFoundException,Exception {
        System.out.println(check);
        JSONObject obj = new JSONObject(check);
        JSONArray arr = obj.getJSONArray("check");
        System.out.println(arr);
        for(int i=0;i<arr.length();i++){
            System.out.println(arr.get(i));
        }
        List<String> ls = new ArrayList<>();
        ls.add("dadad");
        ls.add("dadad");
        ls.add("dadad");
        return ls;
    }

    @RequestMapping(method=RequestMethod.PUT,value="/updateQuantity")
    public String updateQuantity(@RequestBody ProductDetails productDetails) throws ProductNotFoundException {
        return validation.updateQuantity(productDetails);
    }

//    @RequestMapping(method = RequestMethod.GET,value="/topCategories")
//    public List<Cat> getTopCategories() throws CategoryNotFoundException{
//        return categoryService.getTopCategories();
//    }
}

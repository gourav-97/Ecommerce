package com.ecommerce.ecommerce.controller;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.service.CategoryService;
import com.ecommerce.ecommerce.service.ProductsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CustomResponse> showAll(){// throws ProductNotFoundException {
        try{
            List<Product> allProduct= productsService.getAllProduct();
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",allProduct));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"no product found",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/products/{productId}")
    public ResponseEntity<CustomResponse> showById (@PathVariable List<String> productId){// throws ProductNotFoundException {
        try{
            List<Product> product= productsService.getByProductId(productId);
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",product));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"no product found for productId"+productId,null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories")
    public ResponseEntity<CustomResponse> getCategories(){// throws CategoryNotFoundException {
        try{
            List<Cat> allCategories= categoryService.getAllCategories();
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",allCategories));
        }
        catch(CategoryNotFoundException cne){
            return ResponseEntity.status(200).body(new CustomResponse(404,"There are 0 categories in database",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}")
    public ResponseEntity<CustomResponse> getSubCategories(@PathVariable String categoryId){// throws CategoryNotFoundException {
        try{
            List<Cat> subCategories= categoryService.getSubCategories(categoryId);
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",subCategories));
        }
        catch(CategoryNotFoundException cne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"There are 0 categories in database",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}/products")
    public ResponseEntity<CustomResponse> getProductsByCategory(@PathVariable String categoryId){// throws CategoryNotFoundException {
           try{
               List<Product> products = productsService.getProductByCategory(categoryId);
               return ResponseEntity.status(200).body(new CustomResponse(200,"ok",products));
           }
           catch(CategoryNotFoundException cne){
               return ResponseEntity.status(200).body(new CustomResponse(404,"There are no categories/subcategories in database",null));
           }
           //return products;
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}/{subCategoryId}")
    public ResponseEntity<CustomResponse> getProduct(@PathVariable @NotNull String categoryId, @PathVariable String subCategoryId){// throws CategoryNotFoundException, ProductNotFoundException {
        try{
            List<Product> products= categoryService.getProductsInSubCat(subCategoryId);
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",products));
        }
        catch(CategoryNotFoundException | ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"There are no categories/subcategories in database",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/sortByPriceLTH/{subCategoryId}")
    public ResponseEntity<CustomResponse> sortByPriceLTH(@PathVariable  String subCategoryId){
        try{
            List<Product> sortedList= productsService.sortByPriceLTH(subCategoryId);
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",sortedList));
        }
        catch(ProductNotFoundException pne){
            return ResponseEntity.status(200).body(new CustomResponse(404,"there are no products to sort",null));
        }
       // return null;
    }

    @RequestMapping(method=RequestMethod.GET,value="/sortByPriceHTL/{subCategoryId}")
    public ResponseEntity<CustomResponse> sortByPriceHTL(@PathVariable  String subCategoryId){
        try{
            List<Product> sortedList= productsService.sortByPriceHTL(subCategoryId);
            return ResponseEntity.status(200).body(new CustomResponse(200,"ok",sortedList));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"there are no products to sort",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/filterByPopularScore/{subCategoryId}/{score}")
    public ResponseEntity<CustomResponse> filterByPopularScore(@PathVariable("subCategoryId") String subCategoryId, @PathVariable("score") int score){// throws ProductNotFoundException {
        try {
            List<Product> filteredProduct = validation.filterByPopularScore(subCategoryId, score);
            return ResponseEntity.status(200).body(new CustomResponse(200, "ok", filteredProduct));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"No products found",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/displayByPopularScore")
    public ResponseEntity<CustomResponse> displayByPopularScore() { //throws ProductNotFoundException {
        try{
            List<Product> popularProducts = productsService.displayByPopularScore();
            return ResponseEntity.status(200).body(new CustomResponse(200, "ok", popularProducts));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"No products found",null));
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/displayByTopScore/")
    public ResponseEntity<CustomResponse> displayByTopScore(){ // throws CategoryNotFoundException {
        try{
            List<Cat> topcategories = categoryService.displayByTopScore();
            return ResponseEntity.status(200).body(new CustomResponse(200, "ok", topcategories));
        }
        catch(CategoryNotFoundException cne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"No top categories",null));
        }
    }

    @RequestMapping(method=RequestMethod.POST,value = "/getProductsById")
    public ResponseEntity<CustomResponse> getProductsById(@RequestBody List<String> productIds){// throws ProductNotFoundException{
        try{
            List<ProductValidated> products= productsService.getByProductIds(productIds);
            return ResponseEntity.status(200).body(new CustomResponse(200, "ok", products));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"No products found",null));
        }
    }
    @RequestMapping(method=RequestMethod.POST,value="/addCategory")
    public ResponseEntity<CustomResponse> addCategory(@RequestBody CategoryRequest categoryDetails){ //  throws CategoryNotInsertedException {
        try{
            String categoryIdInserted= validation.addCategory(categoryDetails);
            return ResponseEntity.status(200).body(new CustomResponse(200, "category inserted", categoryIdInserted));
        }
        catch(CategoryNotInsertedException cnie){
            return ResponseEntity.status(200).body(new CustomResponse(404,"Category not inserted",null));
        }
    }

    @RequestMapping(method=RequestMethod.POST,value="/addProduct")
    public ResponseEntity<CustomResponse> addProduct(@RequestBody ProductRequest productDetails){// throws ProductNotInsertedException {
        try{
            String productIdInserted = validation.addProduct(productDetails);
            return ResponseEntity.status(200).body(new CustomResponse(200, "product inserted", productIdInserted));
        }
        catch(ProductNotInsertedException pnie)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"product not inserted",null));
        }
    }

//    @RequestMapping(method=RequestMethod.POST,value="/check")
//    public List<String> check(@RequestBody String check) throws ProductNotFoundException,Exception {
//        System.out.println(check);
//        JSONObject obj = new JSONObject(check);
//        JSONArray arr = obj.getJSONArray("check");
//        System.out.println(arr);
//        for(int i=0;i<arr.length();i++){
//            System.out.println(arr.get(i));
//        }
//        List<String> ls = new ArrayList<>();
//        ls.add("dadad");
//        ls.add("dadad");
//        ls.add("dadad");
//        return ls;
//    }

    @RequestMapping(method=RequestMethod.PUT,value="/updateQuantity")
    public ResponseEntity<CustomResponse> updateQuantity(@RequestBody ProductDetails productDetails){ // throws ProductNotFoundException {
        try{
            String updateStatus = validation.updateQuantity(productDetails);
            return ResponseEntity.status(200).body(new CustomResponse(200, updateStatus, "quantity updated"));
        }
        catch(ProductNotFoundException pne)
        {
            return ResponseEntity.status(200).body(new CustomResponse(404,"Enter valid productId","quantity not updated"));
        }
    }

//    @RequestMapping(method = RequestMethod.GET,value="/topCategories")
//    public List<Cat> getTopCategories() throws CategoryNotFoundException{
//        return categoryService.getTopCategories();
//    }
}

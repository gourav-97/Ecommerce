package com.ECommerce.Ecommerce;
import com.ECommerce.Ecommerce.Models.Product;
import com.ECommerce.Ecommerce.Models.Cat;
import com.ECommerce.Ecommerce.Models.ProductNotFoundException;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Service.CategoryService;
import com.ECommerce.Ecommerce.Service.ProductsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.ws.http.HTTPException;
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
    public List<Product> showAll() {
        return productsService.getAllProduct();
    }


    @RequestMapping("/products/{productId}")
    public Product showById (@PathVariable String productId) //throws ProductNotFoundException
    {
       try {

            return productsService.getByProductId(productId);
        } catch (ProductNotFoundException pne){
          pne.printStackTrace();
          String url="http://localhost:8080/categories";
          throw new HTTPException(404);
        }
    }

    @RequestMapping("/categories")
    public List<Cat> getCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping("/categories/{categoryId}")
    public List<Cat> getSubCategories(@PathVariable String categoryId) {

        return categoryService.getSubCategories(categoryId);
    }
    @RequestMapping("/categories/{categoryId}/products")
    public List<Product> getProductsByCategory(@PathVariable String categoryId) {

        return productsService.getProductByCategory(categoryId);
    }

    @RequestMapping("/categories/{categoryId}/{subCategoryId}")
    public List<Product> getProduct(@PathVariable String categoryId,@PathVariable String subCategoryId) throws ProductNotFoundException {
        return categoryService.getProductsInSubCat(categoryId,subCategoryId);
    }


//    @RequestMapping(method = RequestMethod.POST, value = "/products")
//    public String addProduct(@RequestBody @Valid Product product){
//        return product.getProductId();
//    }

}

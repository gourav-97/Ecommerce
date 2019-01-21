package com.ECommerce.Ecommerce.Controller;
import com.ECommerce.Ecommerce.Models.CategoryNotFoundException;
import com.ECommerce.Ecommerce.Models.Product;
import com.ECommerce.Ecommerce.Models.Cat;
import com.ECommerce.Ecommerce.Models.ProductNotFoundException;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Service.CategoryService;
import com.ECommerce.Ecommerce.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;



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

    @RequestMapping(method=RequestMethod.GET,value="/")
    public String Welcome(){
        return "Welcome To The Site";
    }

    @RequestMapping(method=RequestMethod.GET,value="/products")
    public List<Product> showAll() {
        try{
            return productsService.getAllProduct();
        } catch(ProductNotFoundException pne) {
            throw new HTTPException(404);
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/products/{productId}")
    public Product showById (@PathVariable String productId) {
       try {
            return productsService.getByProductId(productId);
        } catch (ProductNotFoundException pne){
          pne.printStackTrace();
          throw new HTTPException(404);
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories")
    public List<Cat> getCategories() {
        try {
            return categoryService.getAllCategories();
        } catch(CategoryNotFoundException cne) {
            throw new HTTPException(404);
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}")
    public List<Cat> getSubCategories(@PathVariable String categoryId) {
        try {
            return categoryService.getSubCategories(categoryId);
        } catch(CategoryNotFoundException cne) {
            throw new HTTPException(404);
        }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}/products")
    public List<Product> getProductsByCategory(@PathVariable String categoryId) {
       try{
           return productsService.getProductByCategory(categoryId);
       } catch(CategoryNotFoundException cne) {
           throw new HTTPException(404);
       }
    }

    @RequestMapping(method=RequestMethod.GET,value="/categories/{categoryId}/{subCategoryId}")
    public List<Product> getProduct(@PathVariable String categoryId,@PathVariable String subCategoryId) throws ProductNotFoundException {
        try {
            return categoryService.getProductsInSubCat(categoryId,subCategoryId);
        } catch (CategoryNotFoundException | ProductNotFoundException cne) {
            throw new HTTPException(404);
        }
    }

    //    @RequestMapping(method = RequestMethod.POST, value = "/products")
//    public String addProduct(@RequestBody @Valid Product product){
//        return product.getProductId();
//    }
}

package com.ECommerce.Ecommerce;
import com.ECommerce.Ecommerce.Models.Products;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import com.ECommerce.Ecommerce.Service.ProductsService;
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

    @Autowired
    ProductsService productsService;

    @RequestMapping("/")
    public String Welcome()
    {
        return "Welcome To The Site";
    }

    @RequestMapping("/all")
    public List<Products> ShowAll() {
        return productsRepository.findAll();
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
        return productsService.getsubCategories(category);
    }

    @RequestMapping("/categories/{category}/{subcategory}")
    public List<Products> Getproducts(@PathVariable String category,@PathVariable String subcategory) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("subCategory").is(subcategory));
//        List<Products> products = mongoTemplate.find(query,Products.class);
//        return products;
        return productsRepository.findBysubCategory(subcategory);
    }

//    @RequestMapping("/categories/{category}/{subcategory}/{id}")
//    public List<Products> getProductsById(@PathVariable String id) {
//        return productsRepository.findBycategory(id);
//    }
    @RequestMapping("/product")
    public List<Products> getProducts() {
        return productsRepository.findAll();
    }

    @RequestMapping("/product/{productid}")
    public List<Products> getProductsById(@PathVariable String productid) {
        return productsRepository.findByproductId(productid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products")
    public String addProduct(@RequestBody @Valid Products product){
        product.set_id(ObjectId.get());
        productsRepository.save(product);
        return product.getProductId();
    }

}

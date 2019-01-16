package com.ECommerce.Ecommerce.Repositories;

import com.ECommerce.Ecommerce.Models.Product;
import com.ECommerce.Ecommerce.Models.Products;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductsRepository extends MongoRepository<Products,String> {

//    List<Products> findAll();
//
//    Products findBy_id(String id);
//
Products findByproductId(String productId);
Products findByProductId(String productId);
////
//    @Query(fields = "{'subCategory':1}")
//    List<Products> findBysubCategory(String name);
//
//    List<Products> findByproductId(String name);
//
//    @Query(value = "{'distinct':'Products','key':'category'}")
////    @Query(fields = "{'category':1}")
//    List<Products> findcategory();
//
}

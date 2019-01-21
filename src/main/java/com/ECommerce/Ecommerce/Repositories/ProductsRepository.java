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
    Products findByproductId(String productId);
    List<Products> findByparentId(String parentId);
}

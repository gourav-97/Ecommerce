package com.ecommerce.ecommerce.repositories;

import com.ecommerce.ecommerce.models.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductsRepository extends MongoRepository<Products,String> {

    Products findByproductId(String productId);
    List<Products> findByparentId(String parentId);
}

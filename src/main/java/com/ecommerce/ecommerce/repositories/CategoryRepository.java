package com.ecommerce.ecommerce.repositories;

import com.ecommerce.ecommerce.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {

    @Query(fields = "{'_id':1}")
    Category findBycategoryName(String name);

    @Query(value = "{'parentId':null}")
    List<Category> findCategoryName();

    @Query(fields = "{'parentId':0}")
    List<Category> findByparentId(String parentId);

    Category findBy_id(String _id);

    List<Category> findBytopScoreGreaterThan(int topScore);
}

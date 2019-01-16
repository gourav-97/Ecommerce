package com.ECommerce.Ecommerce.Service;


import com.ECommerce.Ecommerce.Models.Category;
import com.ECommerce.Ecommerce.Models.Cat;
import com.ECommerce.Ecommerce.Repositories.CategoryRepository;
import com.ECommerce.Ecommerce.Repositories.ProductsRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    MongoTemplate mongoTemplate;
    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;

    public CategoryService(MongoTemplate mongoTemplate, CategoryRepository categoryRepository, ProductsRepository productsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
    }

    public List<Cat> getAllCategories()
    {
        List<Cat> categories = new ArrayList<>();

        List<Category> category = categoryRepository.findCategoryName();
        for(Category c:category)
        {
            Cat newCategory = new Cat(c.getCategoryName(),c.getDesc(),c.getPicURL());
            categories.add(newCategory);
        }
        return categories;
    }

    public List<Cat> getsubCategories(ObjectId categoryId) {
        List<Category> categories = categoryRepository.findByparentId(categoryId);
        List<Cat> subCategories = new ArrayList<>();
        for(Category c:categories)
        {
            Cat category = new Cat(c.getCategoryName(),c.getDesc(),c.getPicURL());
            subCategories.add(category);
        }
        return subCategories;
    }
}

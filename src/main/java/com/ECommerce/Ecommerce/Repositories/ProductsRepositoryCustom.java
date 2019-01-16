package com.ECommerce.Ecommerce.Repositories;

import com.ECommerce.Ecommerce.Models.Products;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductsRepositoryCustom {
    public List<Products> getSubCategories(String category);
}
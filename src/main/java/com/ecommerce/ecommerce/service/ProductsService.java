package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.CategoryRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;
    ProductsRepositoryCustom productsRepositoryCustom;

    public ProductsService(CategoryRepository categoryRepository, ProductsRepository productsRepository, ProductsRepositoryCustom productsRepositoryCustom) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.productsRepositoryCustom = productsRepositoryCustom;
    }

    public String addProduct(ProductRequest productDetails) throws ProductNotInsertedException {
        System.out.println(productDetails.getProductId());
        Products productsObject =new Products(null,productDetails.getParentId(),productDetails.getProductName(),productDetails.getProductId(),productDetails.getBrand(),productDetails.getPrice(),productDetails.getDesc(),productDetails.getQuantity(),productDetails.getGenFeatures(),productDetails.getProdSpecs());
        Products p=productsRepository.insert(productsObject);
        if(p==null)
            throw new ProductNotInsertedException("product was not inserted to database");
        return productsObject.getProductId();
    }

    public List<Product> getAllProduct() throws ProductNotFoundException {
        List<Product> product = new ArrayList<>();
        List<Products> products = productsRepository.findAll();

        if(products.isEmpty())
        {
            throw new ProductNotFoundException("There are 0 products in database");
        }

        for(Products prod:products) {
            Category subCategory = categoryRepository.findBy_id(prod.getParentId());
            String subCategoryName = subCategory.getCategoryName();
            String subCategoryId = subCategory.get_id();
            String subCategoryParentId = subCategory.getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
            Product p = new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs());
            product.add(p);
        }
        return product;
    }

    public Product getByProductId(String productId) throws ProductNotFoundException {
        Products prod = productsRepository.findByproductId(productId);
        if(prod==null) {
            System.out.println("null hai");
            throw new ProductNotFoundException("There is no product belonging to productID ");
        }
        String subCategoryName = categoryRepository.findBy_id(prod.getParentId()).getCategoryName();
        String subCategoryId = categoryRepository.findBycategoryName(subCategoryName).get_id();
        String subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
        String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
        String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();

        return new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs());
    }

    public List<Product> getProductByCategory(String categoryId) throws CategoryNotFoundException {
        Category category = categoryRepository.findBy_id(categoryId);
        List<Category> subCategories = categoryRepository.findByparentId(categoryId);

        if(category==null||subCategories==null)
            throw new CategoryNotFoundException("There are no categories/subcategories in database");

        List<Product> products = new ArrayList<>();
        for(Category subCategoryNew : subCategories)
        {
            List<Products> product = productsRepository.findByparentId(subCategoryNew.get_id());
            for(Products prod: product)
            {
                Product newProduct = new Product(category.getCategoryName(),category.get_id(),subCategoryNew.getCategoryName(),subCategoryNew.get_id(),prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs());
                products.add(newProduct);
            }
        }
        return products;
    }
}

package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.CategoryRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    CategoryRepository categoryRepository;
    ProductsRepository productsRepository;
    ProductsRepositoryCustom productsRepositoryCustom;

    @Autowired
    MongoTemplate mongoTemplate;


    public ProductsService(CategoryRepository categoryRepository, ProductsRepository productsRepository, ProductsRepositoryCustom productsRepositoryCustom) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.productsRepositoryCustom = productsRepositoryCustom;
    }

    public String addProduct(ProductRequest productDetails) throws ProductNotInsertedException {
        System.out.println(productDetails.getProductId());
        Products productsObject =new Products(null,productDetails.getParentId(),productDetails.getProductName(),productDetails.getProductId(),productDetails.getBrand(),productDetails.getPrice(),productDetails.getDesc(),productDetails.getQuantity(),productDetails.getGenFeatures(),productDetails.getProdSpecs(),productDetails.getPopularScore());
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
            if(prod.getQuantity()>0) {
                Product newProduct = new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs(),prod.getPopularScore());
                product.add(newProduct);
            }
        }
        return product;
    }

    public Product getByProductId(String productId) throws ProductNotFoundException {
        Products product = productsRepository.findByproductId(productId);
        if (product == null) {
            throw new ProductNotFoundException("There is no product belonging to productID "+productId);
        }
        String subCategoryName = categoryRepository.findBy_id(product.getParentId()).getCategoryName();
        String subCategoryId = categoryRepository.findBycategoryName(subCategoryName).get_id();
        String subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
        String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
        String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
        return new Product(categoryName,categoryId,subCategoryName,subCategoryId,product.getProductName(),product.getProductId(),product.getBrand(),product.getPrice(),product.getDesc(),product.getQuantity(),product.getGenFeatures(),product.getProdSpecs(),product.getPopularScore());
    }

    public List<Product> getByProductId(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for(String productId :productIds) {
            Products product = productsRepository.findByproductId(productId);
            if (product == null) {
                throw new ProductNotFoundException("There is no product belonging to productID "+productId);
            }
            String subCategoryName = categoryRepository.findBy_id(product.getParentId()).getCategoryName();
            String subCategoryId = categoryRepository.findBycategoryName(subCategoryName).get_id();
            String subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
            if(product.getQuantity()>0) {
                Product newProduct = new Product(categoryName, categoryId, subCategoryName, subCategoryId, product.getProductName(), product.getProductId(), product.getBrand(), product.getPrice(), product.getDesc(), product.getQuantity(), product.getGenFeatures(), product.getProdSpecs(), product.getPopularScore());
                products.add(newProduct);
            }
        }
        return products;
    }

    public List<ProductValidated> getByProductIds(List<String> productIds) throws ProductNotFoundException{

        List<ProductValidated> productsValidated = new ArrayList<>();

        for(String productId:productIds) {
            Product product = getByProductId(productId);
            if(product==null)
                throw new ProductNotFoundException("Some Products were not found");
            ProductValidated productValidated = new ProductValidated(product.getCategory(),product.getCategoryId(),product.getProductName(),product.getProductId(),product.getPrice(),product.getQuantity());
            productsValidated.add(productValidated);
        }
        return productsValidated;
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
                if(prod.getQuantity()>0) {
                    Product newProduct = new Product(category.getCategoryName(), category.get_id(), subCategoryNew.getCategoryName(), subCategoryNew.get_id(), prod.getProductName(), prod.getProductId(), prod.getBrand(), prod.getPrice(), prod.getDesc(), prod.getQuantity(), prod.getGenFeatures(), prod.getProdSpecs(), prod.getPopularScore());
                    products.add(newProduct);
                }
            }
        }
        return products;
    }

    public String updateQuantity(List<ProductDetails> productDetails) throws ProductNotFoundException {
        for(ProductDetails product:productDetails)
        {
            Query query = new Query();
            query.addCriteria((Criteria.where("productId").is(product.getProductId())));
            Products productInDB = productsRepository.findByproductId(product.getProductId());
            if (productInDB == null)
                throw new ProductNotFoundException("there is no product corresponding to productId " + product.getProductId());
            int initialQuantity = productInDB.getQuantity();
            Update update = new Update();
            if(product.isReduce()==true) {
                update.set("quantity", initialQuantity - product.getQuantity());
            }
            else {
                update.set("quantity", initialQuantity + product.getQuantity());
            }
            mongoTemplate.updateFirst(query, update, Products.class, "Products");
        }
        return "quantity updated";
    }

    public List<Products> sortByPriceLTH(String subCategoryId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("categoryId").is(subCategoryId));
        query.with(new Sort(Sort.DEFAULT_DIRECTION,"price"));
        return mongoTemplate.find(query,Products.class);
    }

    public List<Products> sortByPriceHTL(String subCategoryId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("categoryId").is(subCategoryId));
        query.with(new Sort(Sort.Direction.DESC,"price"));
        return mongoTemplate.find(query,Products.class);
    }

    public List<Product> filterByPopularScore(String subCategoryId, int score) throws ProductNotFoundException {
        Query query=new Query();
        query.addCriteria(Criteria.where("subCategoryId").is(subCategoryId).and("popularScore").gte(score));
        List<Products> requiredProducts=mongoTemplate.find(query,Products.class);

        if(requiredProducts.size()==0)
            throw new ProductNotFoundException("there are no products matching your filter");

        List<Product> validProduct=new ArrayList<>();
        for(Products p:requiredProducts)
        {
            String subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String subCategoryName = categoryRepository.findBy_id(p.getParentId()).getCategoryName();
            validProduct.add(new Product(categoryName,subCategoryParentId,subCategoryName,subCategoryId,p.getProductName(),p.getProductId(),p.getBrand(),p.getPrice(),p.getDesc(),p.getQuantity(),p.getGenFeatures(),p.getProdSpecs(),p.getPopularScore()));
            //validProduct.add(new Product(subCategoryId,categoryRepository.));
        }
        return validProduct;

    }
}

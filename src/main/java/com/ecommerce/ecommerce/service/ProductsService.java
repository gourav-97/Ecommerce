package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.exceptions.ProductNotInsertedException;
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
            throw new ProductNotFoundException("there are no products in database");
            //return ResponseEntity.status(200).body(new CustomResponse(404,"there are no products in database",null));
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
        //return ResponseEntity.status(200).body(new CustomResponse(200,"ok",product));
        return product;
    }

    public Product getByProductId(String productId) throws ProductNotFoundException {
        Products product = productsRepository.findByproductId(productId);
        if (product == null) {
            //return ResponseEntity.status(200).body(new CustomResponse(404,"There is no product belonging to productID "+productId,null));
            throw new ProductNotFoundException("There is no product belonging to productID "+productId);
        }
        String subCategoryName = categoryRepository.findBy_id(product.getParentId()).getCategoryName();
        String subCategoryId = categoryRepository.findBycategoryName(subCategoryName).get_id();
        String subCategoryParentId = categoryRepository.findBy_id(subCategoryId).getParentId();
        String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
        String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
        //return ResponseEntity.status(200).body(new CustomResponse(200,"ok",new Product(categoryName,categoryId,subCategoryName,subCategoryId,product.getProductName(),product.getProductId(),product.getBrand(),product.getPrice(),product.getDesc(),product.getQuantity(),product.getGenFeatures(),product.getProdSpecs(),product.getPopularScore())));
        return new Product(categoryName,categoryId,subCategoryName,subCategoryId,product.getProductName(),product.getProductId(),product.getBrand(),product.getPrice(),product.getDesc(),product.getQuantity(),product.getGenFeatures(),product.getProdSpecs(),product.getPopularScore());
    }

    public List<Product> getByProductId(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for(String productId :productIds) {
            Products product = productsRepository.findByproductId(productId);
            if (product == null) {
                //return ResponseEntity.status(200).body(new CustomResponse(404,"There is no product belonging to productID "+productId,null));
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
        //return ResponseEntity.status(200).body(new CustomResponse(200,"ok",products));
        return products;
    }

    public List<ProductValidated> getByProductIds(List<String> productIds) throws ProductNotFoundException{

        List<ProductValidated> productsValidated = new ArrayList<>();

        for(String productId:productIds) {
            Product product = (Product) getByProductId(productId);
            if(product==null)
                //return ResponseEntity.status(200).body(new CustomResponse(404,"Some products were not found "+productId,null));
                throw new ProductNotFoundException("Some Products were not found");
            ProductValidated productValidated = new ProductValidated(product.getCategory(),product.getCategoryId(),product.getProductName(),product.getProductId(),product.getPrice(),product.getQuantity());
            productsValidated.add(productValidated);
        }
        //return ResponseEntity.status(200).body(new CustomResponse(200,"ok",productsValidated));
        return productsValidated;
    }

    public List<Product> getProductByCategory(String categoryId) throws ProductNotFoundException{
//        Category category = categoryRepository.findBy_id(categoryId);
        Category subCategory = categoryRepository.findBy_id(categoryId);
        Category category = categoryRepository.findBy_id(subCategory.getParentId());

        if(subCategory==null || category==null)
            throw new ProductNotFoundException("There are no products in this category");

        List<Product> products = new ArrayList<>();
        List<Products> product = productsRepository.findByparentId(subCategory.get_id());
        for(Products prod: product)
            {
                if(prod.getQuantity()>0) {
                    Product newProduct = new Product(category.getCategoryName(), category.get_id(), subCategory.getCategoryName(), subCategory.get_id(), prod.getProductName(), prod.getProductId(), prod.getBrand(), prod.getPrice(), prod.getDesc(), prod.getQuantity(), prod.getGenFeatures(), prod.getProdSpecs(), prod.getPopularScore());
                    products.add(newProduct);
                }
            }
        return products;
    }

    public String updateQuantity(ProductDetails productDetails) throws ProductNotFoundException {
        for(ProductValidated product:productDetails.getProductIds())
        {
            Query query = new Query();
            query.addCriteria((Criteria.where("productId").is(product.getProductId())));
            Products productInDB = productsRepository.findByproductId(product.getProductId());
            if (productInDB == null)
                throw new ProductNotFoundException("there is no product corresponding to productId " + product.getProductId());
            int initialQuantity = productInDB.getQuantity();
            Update update = new Update();
            if(productDetails.isToReduce()==true) {
                update.set("quantity", initialQuantity - product.getQuantity());
            }
            else {
                update.set("quantity", initialQuantity + product.getQuantity());
            }
            mongoTemplate.updateFirst(query, update, Products.class, "Products");
        }
        return "quantity updated";
    }

    public List<Product> sortByPriceLTH(String subCategoryId) throws ProductNotFoundException {
        Query query=new Query();
        query.addCriteria(Criteria.where("parentId").is(subCategoryId));
        query.with(new Sort(Sort.DEFAULT_DIRECTION,"price"));
        List<Products> sortedProducts= mongoTemplate.find(query,Products.class);
        if(sortedProducts.size()==0)
            throw new ProductNotFoundException("there are no products to sort");
        List<Product> sortedProduct=new ArrayList();
        for(Products prod:sortedProducts)
        {
            Category subCategory = categoryRepository.findBy_id(prod.getParentId());
            String subCategoryName = subCategory.getCategoryName();
            //String subCatId = subCategory.get_id();
            String subCategoryParentId = subCategory.getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
            sortedProduct.add(new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs(),prod.getPopularScore()));
        }
        return sortedProduct;
    }

    public List<Product> sortByPriceHTL(String subCategoryId) throws ProductNotFoundException {
        Query query=new Query();
        query.addCriteria(Criteria.where("parentId").is(subCategoryId));
        query.with(new Sort(Sort.Direction.DESC,"price"));
        List<Products> sortedProducts= mongoTemplate.find(query,Products.class);
        if(sortedProducts.size()==0)
            throw new ProductNotFoundException("There are no products to sort");
        List<Product> sortedProduct=new ArrayList();
        for(Products prod:sortedProducts)
        {
            Category subCategory = categoryRepository.findBy_id(prod.getParentId());
            String subCategoryName = subCategory.getCategoryName();
            //String subCatId = subCategory.get_id();
            String subCategoryParentId = subCategory.getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
            sortedProduct.add(new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs(),prod.getPopularScore()));
        }
        return sortedProduct;
    }

    public List<Product> filterByPopularScore(String subCategoryId, int score) throws ProductNotFoundException {
        Query query=new Query();
        query.addCriteria(Criteria.where("parentId").is(subCategoryId).and("popularScore").gte(score));
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

    public List<Product> displayByPopularScore() throws ProductNotFoundException {
        Query query=new Query();
        query.addCriteria(Criteria.where("popularScore").gte(4));
        List<Products> requiredProducts=mongoTemplate.find(query,Products.class);

//        if(requiredProducts.size()==0)
//            throw new ProductNotFoundException("there are no products matching your filter");
        List<Product> validProduct=new ArrayList<>();
        for(Products prod:requiredProducts)
        {
            Category subCategory = categoryRepository.findBy_id(prod.getParentId());
            String subCategoryId = subCategory.get_id();
            String subCategoryName = subCategory.getCategoryName();
            String subCategoryParentId = subCategory.getParentId();
            String categoryName = categoryRepository.findBy_id(subCategoryParentId).getCategoryName();
            String categoryId = categoryRepository.findBycategoryName(categoryName).get_id();
            validProduct.add(new Product(categoryName,categoryId,subCategoryName,subCategoryId,prod.getProductName(),prod.getProductId(),prod.getBrand(),prod.getPrice(),prod.getDesc(),prod.getQuantity(),prod.getGenFeatures(),prod.getProdSpecs(),prod.getPopularScore()));
        }
        return validProduct;
    }
}

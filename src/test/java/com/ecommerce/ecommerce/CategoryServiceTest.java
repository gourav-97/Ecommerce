package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.CategoryRepository;
import com.ecommerce.ecommerce.repositories.ProductsRepository;
import com.ecommerce.ecommerce.service.CategoryService;
import com.ecommerce.ecommerce.service.ProductsService;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductsService productsService;
    @Mock
    ProductsRepository productsRepository;
    @Mock
    CategoryNotFoundException categoryNotFoundException;
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        categoryService = new CategoryService(mongoTemplate, categoryRepository, productsRepository, productsService);
        categoryNotFoundException = new CategoryNotFoundException(null);
    }


    @Test
    public void getAllCategoriesReturnsNull() throws Exception {
        given(categoryRepository.findCategoryName()).willReturn(null);
        Exception cex = null;
        try {
            List<Cat> categories = categoryService.getAllCategories();
        } catch (Exception ex) {
            cex = ex;
        }
        Assert.assertTrue("Given instance is not of CategoryNotFoundException", cex instanceof CategoryNotFoundException);
    }

    @Test
    public void getCategoriesReturnsCat() throws Exception {

        List<Category> categories = new ArrayList<>();
        ObjectId id = new ObjectId();
        categories.add(new Category(id, "Phone", "", "", "", 4));
        given(categoryRepository.findCategoryName()).willReturn(categories);
        List<Cat> expected = new ArrayList<>();
        for (Category c : categories) {
            expected.add(new Cat(c.getCategoryName(), c.get_id(), c.getDesc(), c.getPicURL(), c.getTopScore()));
        }
        List<Cat> Categories = categoryService.getAllCategories();
        Assert.assertEquals(expected.get(0).getCategoryName(), Categories.get(0).getCategoryName());
        Assert.assertEquals(expected.get(0).getCategoryId(), Categories.get(0).getCategoryId());
        Assert.assertEquals(expected.get(0).getDesc(), Categories.get(0).getDesc());
        Assert.assertEquals(expected.get(0).getPicURL(), Categories.get(0).getPicURL());
        Assert.assertEquals(expected.get(0).getTopScore(), Categories.get(0).getTopScore());
    }

    @Test
    public void getSubCategoriesHasNoParent() throws Exception {

        List<Category> categories = new ArrayList<>();
        given(categoryRepository.findByparentId("")).willReturn(categories);
        Exception cex = null;
        try {
            List<Cat> subCategories = categoryService.getSubCategories("");
        } catch (Exception ex) {
            cex = ex;
        }
        Assert.assertTrue("Given instance is not of CategoryNotFoundException", cex instanceof CategoryNotFoundException);
    }

    @Test
    public void getSubCategoriesReturnsSubCat() throws Exception {

        List<Category> categories = new ArrayList<>();
        ObjectId id = new ObjectId();
        categories.add(new Category(id, "Phone", "", "", "", 4));
        given(categoryRepository.findByparentId("")).willReturn(categories);
        List<Cat> expected = new ArrayList<>();
        System.out.println(categories);
        for (Category c : categories) {
            expected.add(new Cat(c.getCategoryName(), c.get_id(), c.getDesc(), c.getPicURL(), c.getTopScore()));
        }
        List<Cat> subCategories = categoryService.getSubCategories("");
        System.out.println("getSubCategoriesReturnsSubCat()");
        Assert.assertEquals(expected.get(0).getCategoryName(), subCategories.get(0).getCategoryName());
        Assert.assertEquals(expected.get(0).getCategoryId(), subCategories.get(0).getCategoryId());
        Assert.assertEquals(expected.get(0).getDesc(), subCategories.get(0).getDesc());
        Assert.assertEquals(expected.get(0).getPicURL(), subCategories.get(0).getPicURL());
        Assert.assertEquals(expected.get(0).getTopScore(), subCategories.get(0).getTopScore());
    }

    @Test
    public void getProductsInSubCatReturnsEx() throws Exception {
        given(productsRepository.findByparentId("5c48c8300278e02f5522b16e")).willReturn(
                new ArrayList<>()
        );
        Exception cex = null;
        try {
            String id = "5c48c8300278e02f5522b16e";
            List<Product> products = categoryService.getProductsInSubCat(id);
            System.out.println(products.get(0));
        } catch (Exception ex) {
            cex = ex;
            System.out.println(cex);
        }
        Assert.assertTrue(cex.getMessage(), cex instanceof CategoryNotFoundException);
    }

    @Test
    public void addCategory(){}
        Category categoryObj = new Category();
        CategoryRequest req =
}

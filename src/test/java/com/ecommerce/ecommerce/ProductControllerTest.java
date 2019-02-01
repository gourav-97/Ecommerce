package com.ecommerce.ecommerce;
import com.ecommerce.ecommerce.controller.ProductsController;
import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
import com.ecommerce.ecommerce.models.Cat;
import com.ecommerce.ecommerce.models.Category;
import com.ecommerce.ecommerce.models.CustomResponse;
import com.ecommerce.ecommerce.models.Product;
import com.ecommerce.ecommerce.service.CategoryService;
import com.ecommerce.ecommerce.service.ProductsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {


    private ProductsController productsController;

    @Mock
    private ProductsService productsService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryNotFoundException categoryNotFoundException;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        productsController = new ProductsController(categoryService);
        categoryNotFoundException = new CategoryNotFoundException(null);
    }


    @Test
    public void getCategoriesTest() throws Exception{
        List<Cat> categories = new ArrayList<>();

        Cat c = new Cat("Electronics","5c48c6e90278e02f5522b16d","Elec","url",4);
        categories.add(c);
        given(categoryService.getAllCategories()).willReturn(categories);

        ResponseEntity<CustomResponse> resp = productsController.getCategories();
        CustomResponse<List<Cat> > customResponse = resp.getBody();

        List<Cat> expected = customResponse.getResponseData();
        Assert.assertEquals(expected,categories);

        given(categoryService.getAllCategories()).willReturn(null);
        given(categoryService.getAllCategories()).willThrow(categoryNotFoundException);

        ResponseEntity<CustomResponse> resp1 = productsController.getCategories();
        CustomResponse<List<Cat> > customResponse1 = resp1.getBody();

        List<Cat> expected1 = customResponse1.getResponseData();
        int statuscode1 = customResponse1.getStatusCode();
        Assert.assertEquals(expected1,null);
        Assert.assertEquals(statuscode1,404);
    }

    @Test
    public void getSubCategoriesTest()  throws Exception{

        //right id
        List<Cat> subCategories = new ArrayList<>();

        Cat c = new Cat("Phones","5c48c6e90278e02f5522b16d","phone","url",3);
        subCategories.add(c);
        given(categoryService.getSubCategories("5c48c6e90278e02f5522b16d")).willReturn(subCategories);

        ResponseEntity<CustomResponse> resp = productsController.getSubCategories("5c48c6e90278e02f5522b16d");
        CustomResponse<List<Cat> > customResponse = resp.getBody();
        List<Cat> expected = customResponse.getResponseData();
        Assert.assertEquals(expected,subCategories);
        //Wrong ID
        given(categoryService.getSubCategories("123456")).willThrow(categoryNotFoundException);
        ResponseEntity<CustomResponse> resp1 = productsController.getSubCategories("123456");
        CustomResponse<List<Cat> > customResponse1 = resp1.getBody();
        List<Cat> expected1 = customResponse1.getResponseData();
        System.out.println(resp1.getBody().getStatusCode());
        int statuscode1 = customResponse1.getStatusCode();
        Assert.assertEquals(expected1,null);
        Assert.assertEquals(statuscode1,404);
    }

}
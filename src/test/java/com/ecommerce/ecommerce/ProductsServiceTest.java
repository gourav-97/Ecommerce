package com.ecommerce.ecommerce;

        import com.ecommerce.ecommerce.controller.ProductsController;
        import com.ecommerce.ecommerce.exceptions.CategoryNotFoundException;
        import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
        import com.ecommerce.ecommerce.models.Product;
        import com.ecommerce.ecommerce.models.Products;
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

        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.BDDMockito.given;
        import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest
{
    @Mock
    private ProductsService productsService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private ProductNotFoundException productNotFoundException;

    @Mock
    private MongoTemplate mongoTemplate;
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        productNotFoundException = new ProductNotFoundException(null);
        productsService = new ProductsService(categoryRepository,productsRepository,mongoTemplate);
    }

    @Test
    public void getByProductIdTest() throws ProductNotFoundException {
        Products p=new Products(new ObjectId("5c48c8300278e02f5522b16e"),"5c48c8300278e02f5522b16e","Redmi 6","P01","Redmi",8999,"LightWeight Phone With Extended Visual",148,new Object(),new Object(),4);
        Exception cex=null;
        given(productsRepository.findByproductId(any())).willReturn(null);
        try{
            List<Product> product = (List<Product>) productsService.getByProductId("P01");
        }catch(Exception ex){
            cex=ex;
        }
        Assert.assertTrue("Given instance is not of ProductNotFoundException", cex instanceof ProductNotFoundException);
//
    }
}

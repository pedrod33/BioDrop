package pt.deliveries.business_iniciative.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_iniciative.contoller.ProductRestController;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.service.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductRestController.class)
class ProductRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceImpl service;


    @Test
    void whenFindAllProducts_thenReturnAllProducts( ) throws Exception {

        Product prod1 = new Product(1L, "prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product(2L, "prod2", "origin2", 10, "path", 100 );

        List<Product> products = new ArrayList<>();
        products.add(prod1);
        products.add(prod2);

        when( service.findAllProducts() ).thenReturn( products );

        mvc.perform(get("/businesses-api/products").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"prod1\",\n" +
                        "        \"origin\": \"origin1\",\n" +
                        "        \"price\": 10.0,\n" +
                        "        \"imgPath\": \"path\",\n" +
                        "        \"weight\": 100.0\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"name\": \"prod2\",\n" +
                        "        \"origin\": \"origin2\",\n" +
                        "        \"price\": 10.0,\n" +
                        "        \"imgPath\": \"path\",\n" +
                        "        \"weight\": 100.0\n" +
                        "    }\n" +
                        "]"));

        verify(service, times(1)).findAllProducts();
    }

    @Test
    void whenSaveValidProduct_thenReturnProduct( ) throws Exception {


    }

}
package pt.deliveries.business_iniciative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_iniciative.JsonUtil;
import pt.deliveries.business_iniciative.contoller.ProductRestController;
import pt.deliveries.business_iniciative.model.Address;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.service.ProductServiceImpl;

import java.util.*;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


@WebMvcTest(ProductRestController.class)
class ProductRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceImpl service;


    @Test
    void whenFindAllProducts_thenReturnAllProducts( ) throws Exception {

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod2.setId(2L);

        List<Product> products = new ArrayList<>();
        products.add(prod1);
        products.add(prod2);

        when( service.findAllProducts() ).thenReturn( products );

        mvc.perform(get("/businesses-api/products").contentType(MediaType.APPLICATION_JSON))
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
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        store.setProducts(new HashSet<>(Collections.singletonList(prod1)));

        when( service.saveProd(Mockito.any(), eq(store.getId())) ).thenReturn( store );

        mvc.perform(post("/businesses-api/saveProduct?storeId=1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(prod1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("store1")))
                .andExpect(jsonPath("address.id", is(1)))
                .andExpect(jsonPath("address.city", is("city")))
                .andExpect(jsonPath("address.address", is("address")))
                .andExpect(jsonPath("address.latitude", is(10.0)))
                .andExpect(jsonPath("address.longitude", is(11.0)))
                .andExpect(jsonPath("$.products[0].id", is(1)))
                .andExpect(jsonPath("$.products[0].name", is("prod1")))
                .andExpect(jsonPath("$.products[0].origin", is("origin1")))
                .andExpect(jsonPath("$.products[0].price", is(10.0)))
                .andExpect(jsonPath("$.products[0].imgPath", is("path")))
                .andExpect(jsonPath("$.products[0].weight", is(100.0)));

        verify(service, times(1)).saveProd(Mockito.any(), eq(store.getId()));
    }

    @Test
    void whenSaveInvalidProduct_thenReturnProduct( ) throws Exception {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        store.setProducts(new HashSet<>(Collections.singletonList(prod1)));

        when( service.saveProd(Mockito.any(), eq(2L)) ).thenReturn( null );

        mvc.perform(post("/businesses-api/saveProduct?storeId=2").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(prod1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());

        verify(service, times(1)).saveProd(Mockito.any(), eq(2L));
    }

}
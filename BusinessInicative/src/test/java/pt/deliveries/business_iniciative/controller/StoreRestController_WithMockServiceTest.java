package pt.deliveries.business_iniciative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_iniciative.JsonUtil;
import pt.deliveries.business_iniciative.contoller.ClientRestController;
import pt.deliveries.business_iniciative.contoller.StoreRestController;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.service.ClientServiceImpl;
import pt.deliveries.business_iniciative.service.StoreServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StoreRestController.class)
public class StoreRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoreServiceImpl service;


    @Test
    void whenFindAllProductsInStoreWithValidStoreId_thenReturnProducts() throws Exception {
        Store store = new Store(1L, "store1", "address", null, 10, 11);

        Product prod1 = new Product(1L, "prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product(2L, "prod2", "origin2", 10, "path", 100 );

        Set<Product> productsInStore = new HashSet<>();
        productsInStore.add(prod1);
        productsInStore.add(prod2);

        store.setProducts(productsInStore);


        when( service.findAllProductsInStore(store.getId()) ).thenReturn( productsInStore );


        mvc.perform(get("/businesses-api/productsIn?storeId=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\n" +
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
                        "    }]"));

        verify(service, times(1)).findAllProductsInStore(store.getId());
    }

    @Test
    void whenFindAllProductsInStoreWithInvalidStoreId_thenReturnEmptyResponse() throws Exception {

        when( service.findAllProductsInStore( Mockito.any() )).thenReturn( null );


        mvc.perform(get("/businesses-api/productsIn?storeId=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(service, times(1)).findAllProductsInStore( Mockito.any() );
    }

    @Test
    void whenFindStoreWithValidName_thenReturnStore() throws Exception {
        Store store = new Store(1L, "store1", "address", null, 10, 11);
        List<Store> stores_found = new ArrayList<>();
        stores_found.add(store);

        when( service.findByName( store.getName() )).thenReturn( stores_found );

        mvc.perform(get("/businesses-api/store-name?name=store1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"store1\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"products\": null,\n" +
                        "    \"latitude\": 10.0,\n" +
                        "    \"longitude\": 11.0\n" +
                        "}]"));

        verify(service, times(1)).findByName( store.getName() );
    }

    @Test
    void whenFindStoreWithInvalidName_thenReturnEmpty() throws Exception {

        when( service.findByName( "wrong_name" )).thenReturn( new ArrayList<>() );

        mvc.perform(get("/businesses-api/store-name?name=wrong_name").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(service, times(1)).findByName( "wrong_name" );
    }

    @Test
    void whenFindStoreWithValidAddress_thenReturnStore() throws Exception {
        Store store = new Store(1L, "store1", "address", null, 10, 11);
        List<Store> stores_found = new ArrayList<>();
        stores_found.add(store);

        when( service.findByAddress( store.getAddress() )).thenReturn( stores_found );

        mvc.perform(get("/businesses-api/store-address?address=address").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"store1\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"products\": null,\n" +
                        "    \"latitude\": 10.0,\n" +
                        "    \"longitude\": 11.0\n" +
                        "}]"));

        verify(service, times(1)).findByAddress( store.getAddress() );
    }

    @Test
    void whenFindStoreWithInvalidAddress_thenReturnEmpty() throws Exception {

        when( service.findByAddress( "wrong_address" )).thenReturn( new ArrayList<>() );

        mvc.perform(get("/businesses-api/store-address?address=wrong_address").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(service, times(1)).findByAddress( "wrong_address" );
    }

    @Test
    void whenFindStoreWithValidCoords_thenReturnStore() throws Exception {
        Store store = new Store(1L, "store1", "address", null, 10, 11);

        when( service.findByLatAndLng( store.getLatitude(), store.getLongitude() )).thenReturn( store );

        mvc.perform(get("/businesses-api/store-coords?lat=10&lng=11").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"store1\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"products\": null,\n" +
                        "    \"latitude\": 10.0,\n" +
                        "    \"longitude\": 11.0\n" +
                        "}"));

        verify(service, times(1)).findByLatAndLng( store.getLatitude(), store.getLongitude() );
    }

    @Test
    void whenFindStoreWithInvalidCoords_thenReturnEmpty() throws Exception {

        when( service.findByLatAndLng( 0, 0 )).thenReturn( null );

        mvc.perform(get("/businesses-api/store-coords?lat=0&lng=0").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(service, times(1)).findByLatAndLng( 0, 0 );
    }

    /* TODO: Teste errado? no response?
    @Test
    void whenPostValidStore_thenReturnStore() throws Exception {
        Store store = new Store(1L, "store1", "address", null, 10, 11);

        when( service.save( store )).thenReturn( store );

        mvc.perform(post("/businesses-api/saveStore").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(store)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"store 1\",\n" +
                        "    \"address\": \"store 1 address\",\n" +
                        "    \"products\": null,\n" +
                        "    \"latitude\": 1000.0,\n" +
                        "    \"longitude\": 1111.0\n" +
                        "}"));

        verify(service, times(1)).save( Mockito.any() );
    }
     */
}

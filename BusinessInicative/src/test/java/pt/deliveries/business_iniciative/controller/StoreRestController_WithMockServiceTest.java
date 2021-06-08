package pt.deliveries.business_iniciative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_iniciative.JsonUtil;
import pt.deliveries.business_iniciative.contoller.StoreRestController;
import pt.deliveries.business_iniciative.model.Address;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.service.StoreServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
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
    public void given3Stores_whenFindAllStores_thenReturnAllStores() throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        Address address2 = new Address("city2", "address2", 10, 11);
        Address address3 = new Address("city3", "address3", 10, 11);
        address1.setId(1L);
        address2.setId(2L);
        address3.setId(3L);


        Set<Product> products = new HashSet<>();
        Store store1 = new Store("store1", null, products);
        Store store2 = new Store("store2", null, products);
        Store store3 = new Store("store3", null, products);
        store1.setAddress(address1);
        store2.setAddress(address2);
        store3.setAddress(address3);

        List<Store> allStores = new ArrayList<>();
        allStores.add(store1);
        allStores.add(store2);
        allStores.add(store3);

        when( service.findAllStores() ).thenReturn( allStores );


        mvc.perform(get("/businesses-api/stores").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", is("store1")))
                .andExpect(jsonPath("$.[1].name", is("store2")))
                .andExpect(jsonPath("$.[2].name", is("store3")));

        verify(service, times(1)).findAllStores();

        assertThat(allStores).hasSize(3).extracting(Store::getName).contains(store1.getName(), store2.getName(), store3.getName());
    }
    
    @Test
    void whenFindAllProductsInStoreWithValidStoreId_thenReturnProducts() throws Exception {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod2.setId(2L);

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
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        List<Store> stores_found = new ArrayList<>();
        stores_found.add(store);

        when( service.findByName( store.getName() )).thenReturn( stores_found );

        mvc.perform(get("/businesses-api/store-name?name=store1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]name", is("store1")))
                .andExpect(jsonPath("$.[0]address.id", is(1)))
                .andExpect(jsonPath("$.[0]address.city", is("city")))
                .andExpect(jsonPath("$.[0]address.address", is("address")))
                .andExpect(jsonPath("$.[0]address.latitude", is(10.0)))
                .andExpect(jsonPath("$.[0]address.longitude", is(11.0)));

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
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        List<Store> stores_found = new ArrayList<>();
        stores_found.add(store);

        when( service.findByAddress( store.getAddress().getAddress() )).thenReturn( stores_found );

        mvc.perform(get("/businesses-api/store-address?address=address").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]name", is("store1")))
                .andExpect(jsonPath("$.[0]address.id", is(1)))
                .andExpect(jsonPath("$.[0]address.city", is("city")))
                .andExpect(jsonPath("$.[0]address.address", is("address")))
                .andExpect(jsonPath("$.[0]address.latitude", is(10.0)))
                .andExpect(jsonPath("$.[0]address.longitude", is(11.0)));

        verify(service, times(1)).findByAddress( store.getAddress().getAddress() );
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
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        when( service.findByLatAndLng( store.getAddress().getLatitude(), store.getAddress().getLongitude() )).thenReturn( store );

        mvc.perform(get("/businesses-api/store-coords?lat=10&lng=11").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("store1")))
                .andExpect(jsonPath("address.id", is(1)))
                .andExpect(jsonPath("address.city", is("city")))
                .andExpect(jsonPath("address.address", is("address")))
                .andExpect(jsonPath("address.latitude", is(10.0)))
                .andExpect(jsonPath("address.longitude", is(11.0)));

        verify(service, times(1)).findByLatAndLng( store.getAddress().getLatitude(), store.getAddress().getLongitude() );
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

    @Test
    void whenPostValidStore_thenReturnStore() throws Exception {
        Address address = new Address("city", "address", 10, 11);

        Store store = new Store("store1", null, null);
        store.setAddress(address);


        when( service.saveStore( Mockito.any()) ).thenReturn( store );


        mvc.perform(post("/businesses-api/saveStore").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(store)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("store1")))
                .andExpect(jsonPath("address.city", is("city")))
                .andExpect(jsonPath("address.address", is("address")))
                .andExpect(jsonPath("address.latitude", is(10.0)))
                .andExpect(jsonPath("address.longitude", is(11.0)));

        verify(service, times(1)).saveStore( Mockito.any() );
    }

}

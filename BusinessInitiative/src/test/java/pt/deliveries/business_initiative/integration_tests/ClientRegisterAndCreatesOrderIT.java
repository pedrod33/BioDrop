package pt.deliveries.business_initiative.integration_tests;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.BusinessInitiativeApplication;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.exception.ClientEmailOrPasswordIncorrectException;
import pt.deliveries.business_initiative.exception.ClientEmailOrPhoneNumberInUseException;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.pojo.AddressSaveForClientPOJO;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
import pt.deliveries.business_initiative.repository.AddressRepository;
import pt.deliveries.business_initiative.repository.ClientRepository;
import pt.deliveries.business_initiative.repository.OrderRepository;
import pt.deliveries.business_initiative.repository.StoreRepository;
import pt.deliveries.business_initiative.service.StoreServiceImpl;


import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BusinessInitiativeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientRegisterAndCreatesOrderIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StoreServiceImpl storeService;


    @Order(1)
    @Test
    void contextLoads() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);
        store.setProducts(new HashSet<>(Collections.singletonList(prod1)));

        System.out.println(storeService.save(store));
        System.out.println("Context loads!");
    }


    @Order(2)
    @Test
    void whenValidRegisterInput_thenStatus201( ) throws Exception {
        ClientRegistrationPOJO goodClientPOJO = new ClientRegistrationPOJO("cunha1", "cunha1@ua.pt", "1234", "M", "96000001");


        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClientPOJO)))
                .andDo(print())
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("cunha1")))
                .andExpect(jsonPath("$.email", is("cunha1@ua.pt")))
                .andExpect(jsonPath("$.password", is("1234")))
                .andExpect(jsonPath("$.gender", is("M")))
                .andExpect(jsonPath("$.phoneNumber", is("96000001")));
    }

    @Order(3)
    @Test
    void whenRegisterWithUsedEmail_thenStatus201( ) throws Exception {
        ClientRegistrationPOJO badClient = new ClientRegistrationPOJO("cunha1", "cunha1@ua.pt", "1234", "M", "9600000-");


        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPhoneNumberInUseException));
    }

    @Order(4)
    @Test
    void whenRegisterWithUsedPhoneNumber_thenStatus201( ) throws Exception {
        ClientRegistrationPOJO badClient = new ClientRegistrationPOJO("cunha1", "cunha-@ua.pt", "1234", "M", "96000001");


        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPhoneNumberInUseException));
    }

    @Test
    @Order(5)
    void given1Client_whenFindAllClients_thenReturnAllClients() throws Exception {
        mvc.perform(get("/businesses-api/clients/allClients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.[0].id", is(4)))
                .andExpect(jsonPath("$.[0].name", is("cunha1")))
                .andExpect(jsonPath("$.[0].email", is("cunha1@ua.pt")))
                .andExpect(jsonPath("$.[0].phoneNumber", is("96000001")));
    }


    @Test
    @Order(6)
    void whenValidLoginInput_thenStatus200( ) throws Exception {
        ClientLoginPOJO goodClient = new ClientLoginPOJO("cunha1@ua.pt", "1234");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClient)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id", is(9)))
                .andExpect(jsonPath("$.name", is("cunha1")))
                .andExpect(jsonPath("$.email", is("cunha1@ua.pt")))
                .andExpect(jsonPath("$.password", is("1234")))
                .andExpect(jsonPath("$.gender", is("M")))
                .andExpect(jsonPath("$.phoneNumber", is("96000001")));
    }

    @Order(7)
    @Test
    void whenInvalidLoginEmail_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("---@ua.pt", "1234");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));
    }

    @Order(8)
    @Test
    void whenInvalidLoginPassword_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("cunha1@ua.pt", "----");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));
    }

    @Test
    @Order(9)
    void whenUpdateClientAddress_thenStatus200( ) throws Exception {
        AddressSaveForClientPOJO address1 = new AddressSaveForClientPOJO("city1", "address1", 10, 11);

        mvc.perform(put("/businesses-api/addresses/update-client-address?clientId=9").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(address1)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("id", is(9)))
                .andExpect(jsonPath("name", is("cunha1")))
                .andExpect(jsonPath("email", is("cunha1@ua.pt")))
                .andExpect(jsonPath("password", is("1234")))
                .andExpect(jsonPath("addresses.[0].id", is(10)))
                .andExpect(jsonPath("addresses.[0].city", is("city1")))
                .andExpect(jsonPath("addresses.[0].completeAddress", is("address1")))
                .andExpect(jsonPath("addresses.[0].latitude", is(10.0)))
                .andExpect(jsonPath("addresses.[0].longitude", is(11.0)))
                .andExpect(jsonPath("gender", is("M")))
                .andExpect(jsonPath("phoneNumber", is("96000001")));
    }

    @Test
    @Order(10)
    void whenUpdateProductsInOrder_thenStatus200() throws Exception {

        mvc.perform(put("/businesses-api/orders/updateOrder?clientId=9&productId=8&amount=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            //.andExpect(jsonPath("id", is(11)))
            //.andExpect(jsonPath("address.city", is("city1")))
            //.andExpect(jsonPath("address.completeAddress", is("address1")))
            //.andExpect(jsonPath("address.latitude", is(10.0)))
            //.andExpect(jsonPath("address.longitude", is(11.0)))
            .andExpect(jsonPath("orderProducts.[0].product.name", is("prod1")))
            .andExpect(jsonPath("orderProducts.[0].product.origin", is("origin1")))
            .andExpect(jsonPath("orderProducts.[0].product.price", is(10.0)))
            .andExpect(jsonPath("orderProducts.[0].product.imgPath", is("path")))
            .andExpect(jsonPath("orderProducts.[0].product.weight", is(100.0)))
            .andExpect(jsonPath("orderProducts.[0].amount", is(1)))
            .andExpect(jsonPath("status", is("waiting")));
    }

}

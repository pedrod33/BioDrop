package pt.deliveries.business_initiative.integration_tests;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
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
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
import pt.deliveries.business_initiative.repository.ClientRepository;
import pt.deliveries.business_initiative.service.ClientService;
import pt.deliveries.business_initiative.service.ClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BusinessInitiativeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository repository;

    /*
    @BeforeEach
    public void setUpDb() {
        Client goodClient1 = new Client("cunha6", "cunha6@ua.pt", "1234", null, "M", "96000006");
        Client goodClient2 = new Client("cunha7", "cunha7@ua.pt", "1234", null, "M", "96000007");
        Client goodClient3 = new Client("cunha8", "cunha8@ua.pt", "1234", null, "M", "96000008");
        goodClient1.setId(6L);
        goodClient2.setId(7L);
        goodClient3.setId(8L);

        repository.save(goodClient1);
        repository.save(goodClient2);
        repository.save(goodClient3);
    }

    //@BeforeEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Order(1)
    @Test
    void given3Clients_whenFindAllClients_thenReturnAllClients() throws Exception {
        mvc.perform(get("/businesses-api/clients/allClients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(6)))
                .andExpect(jsonPath("$.[0].name", is("cunha6")))
                .andExpect(jsonPath("$.[0].phoneNumber", is("96000006")))
                .andExpect(jsonPath("$.[1].id", is(7)))
                .andExpect(jsonPath("$.[1].name", is("cunha7")))
                .andExpect(jsonPath("$.[1].phoneNumber", is("96000007")))
                .andExpect(jsonPath("$.[2].id", is(8)))
                .andExpect(jsonPath("$.[2].name", is("cunha8")))
                .andExpect(jsonPath("$.[2].phoneNumber", is("96000008")));
    }

    @Order(2)
    @Test
    void whenValidRegisterInput_thenStatus201( ) throws Exception {
        ClientRegistrationPOJO goodClientPOJO = new ClientRegistrationPOJO("cunha9", "cunha9@ua.pt", "1234", "M", "96000009");

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClientPOJO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("cunha9")))
                .andExpect(jsonPath("$.email", is("cunha9@ua.pt")))
                .andExpect(jsonPath("$.password", is("1234")))
                .andExpect(jsonPath("$.gender", is("M")))
                .andExpect(jsonPath("$.phoneNumber", is("96000009")));
    }

    @Order(3)
    @Test
    void  whenRegisterWithUsedEmail_thenStatus226( ) throws Exception {
        ClientRegistrationPOJO badClient = new ClientRegistrationPOJO("cunha1", "cunha9@ua.pt", "1234", "M", "96100000");

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPhoneNumberInUseException));
    }

    @Order(4)
    @Test
    void  whenRegisterWithUsedPhoneNumber_thenStatus226( ) throws Exception {
        ClientRegistrationPOJO badClient = new ClientRegistrationPOJO("cunha10", "cunha10@ua.pt", "1234", "M", "96000009");

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPhoneNumberInUseException));
    }

    @Order(5)
    @Test
    void  whenValidLoginInput_thenStatus200( ) throws Exception {
        ClientLoginPOJO goodClient = new ClientLoginPOJO("cunha9@ua.pt", "1234");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("cunha9")))
                .andExpect(jsonPath("$.email", is("cunha9@ua.pt")))
                .andExpect(jsonPath("$.password", is("1234")))
                .andExpect(jsonPath("$.gender", is("M")))
                .andExpect(jsonPath("$.phoneNumber", is("96000009")));
    }

    @Order(6)
    @Test
    void whenInvalidLoginEmail_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("---@ua.pt", "1234");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));
    }

    @Order(7)
    @Test
    void whenInvalidLoginPassword_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("cunha@ua.pt", "----");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));
    }

    @Order(8)
    @Test
    void whenInvalidLoginEmailAndPassword_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("----@ua.pt", "----");

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));
    }
    */
}

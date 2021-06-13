package pt.deliveries.business_initiative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.exception.ClientEmailOrPasswordIncorrectException;
import pt.deliveries.business_initiative.exception.ClientEmailOrPhoneNumberInUseException;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
import pt.deliveries.business_initiative.service.ClientServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientRestController.class)
class ClientRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientServiceImpl service;


    @Test
    void given3Clients_whenFindAllClients_thenReturnAllClients() throws Exception {
        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        Client goodClient2 = new Client("cunha2", "cunha@ua.pt", "1234", null, "M", "96000002");
        Client goodClient3 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(1L);
        goodClient2.setId(2L);
        goodClient3.setId(3L);
        List<Client> expectedClients = new ArrayList<>(Arrays.asList(goodClient1, goodClient2, goodClient3));


        when( service.findAll() ).thenReturn(expectedClients);


        mvc.perform(get("/businesses-api/clients/allClients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is("cunha1")))
                .andExpect(jsonPath("$.[0].phoneNumber", is("96000001")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].name", is("cunha2")))
                .andExpect(jsonPath("$.[1].phoneNumber", is("96000002")))
                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].name", is("cunha3")))
                .andExpect(jsonPath("$.[2].phoneNumber", is("96000003")));

        verify(service, times(1)).findAll();
    }

    @Test
    void whenValidRegisterInput_thenStatus201( ) throws Exception {
        Client goodClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        goodClient.setId(1L);
        ClientRegistrationPOJO goodClientPOJO = new ClientRegistrationPOJO("cunha", "cunha@ua.pt", "1234", "M", "96000000");


        when( service.registerClient( goodClientPOJO )).thenReturn(goodClient);


        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClientPOJO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

        verify(service, times(1)).registerClient( goodClientPOJO );
    }

    @Test
    void  whenInvalidRegisterInput_thenStatus226( ) throws Exception {
        Client badClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        badClient.setId(1L);

        doThrow(ClientEmailOrPhoneNumberInUseException.class)
                .when(service).verifyRegister(Mockito.any());

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPhoneNumberInUseException));

        verify(service, times(1)).verifyRegister(Mockito.any());
    }

    @Test
    void  whenValidLoginInput_thenStatus200( ) throws Exception {
        Client goodClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        goodClient.setId(1L);

        when( service.verifyLogin( Mockito.any()) ).thenReturn(goodClient);

        String body = "{\"email\":\"cunha@ua.pt\", \"password\": \"1234\"}";
        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenInvalidLoginEmail_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("---@ua.pt", "1234");

        when( service.verifyLogin( badClientPOJO )).thenThrow(ClientEmailOrPasswordIncorrectException.class);

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));

        verify(service, times(1)).verifyLogin( badClientPOJO );
    }

    @Test
    void whenInvalidLoginPassword_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("cunha@ua.pt", "----");

        when( service.verifyLogin( badClientPOJO )).thenThrow(ClientEmailOrPasswordIncorrectException.class);

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));

        verify(service, times(1)).verifyLogin( badClientPOJO );
    }

    @Test
    void whenInvalidLoginEmailAndPassword_thenStatus403( ) throws Exception {
        ClientLoginPOJO badClientPOJO = new ClientLoginPOJO("----@ua.pt", "----");

        when( service.verifyLogin( badClientPOJO )).thenThrow(ClientEmailOrPasswordIncorrectException.class);

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClientPOJO)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ClientEmailOrPasswordIncorrectException));

        verify(service, times(1)).verifyLogin( badClientPOJO );
    }
}
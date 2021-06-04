package pt.deliveries.business_iniciative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pt.deliveries.business_iniciative.contoller.ClientRestController;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.service.ClientService;
import pt.deliveries.business_iniciative.service.ClientServiceImpl;

import java.util.HashMap;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientRestController.class)
class ClientRestController_WithMockServiceIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientServiceImpl service;


    @Test
    void whenPostGoodClientRegister_thenStatus201( ) throws Exception {

        Client goodClient = new Client(1L, "cunha", "cunha@ua.pt", "1234", "address", "M", "96000000");

        when( service.exists( Mockito.any()) ).thenReturn( false );
        when( service.save( Mockito.any()) ).thenReturn( goodClient );

        String body =
                "{\n" +
                "    \"name\":\"cunha\",\n" +
                "    \"email\":\"cunha@ua.pt\",\n" +
                "    \"password\":\"ola\",\n" +
                "    \"address\":\"address\",\n" +
                "    \"gender\":\"M\",\n" +
                "    \"phoneNumber\":\"9100000\"\n" +
                "}";

        mvc.perform(post("/businesses-api/register").contentType(MediaType.APPLICATION_JSON).content(body)) //.content(JsonUtil.toJson(bob))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void whenPostAlreadyRegisteredClient_thenStatus226( ) throws Exception {

        when( service.exists( Mockito.any()) ).thenReturn( true );
        String body =
                "{\n" +
                "    \"name\":\"cunha\",\n" +
                "    \"email\":\"cunha@ua.pt\",\n" +
                "    \"password\":\"ola\",\n" +
                "    \"address\":\"address\",\n" +
                "    \"gender\":\"M\",\n" +
                "    \"phoneNumber\":\"9100000\"\n" +
                "}";

        mvc.perform(post("/businesses-api/register").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isImUsed())
                .andExpect(content().json(body));

        verify(service, times(1)).exists(Mockito.any());
    }


    @Test
    void whenPostCorrectLogin_thenStatus200( ) throws Exception {

        Client goodClient = new Client(1L, "cunha", "cunha@ua.pt", "1234", "address", "M", "96000000");

        when( service.verifyLogin( Mockito.any()) ).thenReturn( goodClient );

        String body = "{\"email\":\"cunha@ua.pt\", \"password\": \"1234\"}";

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenPostIncorrectEmail_thenStatus403( ) throws Exception {

        Client badClient = new Client(null, null, "----@ua.pt", "1234", null, null, null);

        when( service.verifyLogin( Mockito.any()) ).thenReturn( badClient );

        String body = "{\"email\":\"----@ua.pt\", \"password\": \"1234\"}";

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": null,\n" +
                        "    \"name\": null,\n" +
                        "    \"email\": \"----@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": null,\n" +
                        "    \"gender\": null,\n" +
                        "    \"phoneNumber\": null\n" +
                        "}"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenPostIncorrectPassword_thenStatus403( ) throws Exception {

        Client badClient = new Client(null, null, "cunha@ua.pt", "----", null, null, null);

        when( service.verifyLogin( Mockito.any()) ).thenReturn( badClient );

        String body = "{\"email\":\"cunha@ua.pt\", \"password\": \"----\"}";

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": null,\n" +
                        "    \"name\": null,\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"----\",\n" +
                        "    \"address\": null,\n" +
                        "    \"gender\": null,\n" +
                        "    \"phoneNumber\": null\n" +
                        "}"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenPostIncorrectEmailAndPassword_thenStatus403( ) throws Exception {

        Client badClient = new Client(null, null, "----@ua.pt", "----", null, null, null);

        when( service.verifyLogin( Mockito.any()) ).thenReturn( badClient );

        String body = "{\"email\":\"----@ua.pt\", \"password\": \"----\"}";

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(body)) //.content(JsonUtil.toJson(bob))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": null,\n" +
                        "    \"name\": null,\n" +
                        "    \"email\": \"----@ua.pt\",\n" +
                        "    \"password\": \"----\",\n" +
                        "    \"address\": null,\n" +
                        "    \"gender\": null,\n" +
                        "    \"phoneNumber\": null\n" +
                        "}"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }
}
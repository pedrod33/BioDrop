package pt.deliveries.business_iniciative.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_iniciative.JsonUtil;
import pt.deliveries.business_iniciative.contoller.ClientRestController;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.service.ClientServiceImpl;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientRestController.class)
class ClientRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientServiceImpl service;


    @Test
    void whenValidRegisterInput_thenStatus201( ) throws Exception {

        Client goodClient = new Client(1L, "cunha", "cunha@ua.pt", "1234", "address", "M", "96000000");

        when( service.verifyRegister( Mockito.any()) ).thenReturn( true );
        when( service.save( Mockito.any()) ).thenReturn(goodClient);

        mvc.perform(post("/businesses-api/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClient)))
                .andDo(print())
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
    void  whenInvalidRegisterInput_thenStatus226( ) throws Exception {
        Client badClient = new Client("cunha", "cunha@ua.pt", "1234", "address", "M", "96000000");

        when( service.verifyRegister( Mockito.any()) ).thenReturn( false );

        mvc.perform(post("/businesses-api/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(content().string("Email ou numero de telefone ja se encontram em uso."));

        verify(service, times(1)).verifyRegister(Mockito.any());
    }

    @Test
    void  whenValidLoginInput_thenStatus200( ) throws Exception {

        Client goodClient = new Client(1L, "cunha", "cunha@ua.pt", "1234", "address", "M", "96000000");

        when( service.verifyLogin( Mockito.any()) ).thenReturn(goodClient);

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
    void whenInvalidLoginEmail_thenStatus403( ) throws Exception {

        Client badClient = new Client(null, null, "----@ua.pt", "1234", null, null, null);

        when( service.verifyLogin( Mockito.any()) ).thenReturn(null);

        String body = "{\"email\":\"----@ua.pt\", \"password\": \"1234\"}";

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Email ou password incorretos"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenInvalidLoginPassword_thenStatus403( ) throws Exception {

        when( service.verifyLogin( Mockito.any()) ).thenReturn(null);

        String body = "{\"email\":\"cunha@ua.pt\", \"password\": \"----\"}";

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Email ou password incorretos"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenInvalidLoginEmailAndPassword_thenStatus403( ) throws Exception {
        Client badClient = new Client(null, null, "----@ua.pt", "----", null, null, null);

        when( service.verifyLogin( Mockito.any()) ).thenReturn(null);

        mvc.perform(post("/businesses-api/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Email ou password incorretos"));

        verify(service, times(1)).verifyLogin(Mockito.any());
    }
}
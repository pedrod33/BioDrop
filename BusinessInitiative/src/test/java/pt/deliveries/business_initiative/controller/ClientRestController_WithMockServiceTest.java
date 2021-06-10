package pt.deliveries.business_initiative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.service.ClientServiceImpl;

import static org.mockito.Mockito.*;
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
        Client goodClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        goodClient.setId(1L);

        when( service.verifyRegister( Mockito.any()) ).thenReturn( true );
        when( service.save( Mockito.any()) ).thenReturn(goodClient);

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(goodClient)))
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

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void  whenInvalidRegisterInput_thenStatus226( ) throws Exception {
        Client badClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        badClient.setId(1L);

        when( service.verifyRegister( Mockito.any()) ).thenReturn( false );

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isImUsed())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

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
        String body = "{\"email\":\"----@ua.pt\", \"password\": \"1234\"}";

        when( service.verifyLogin( Mockito.any()) ).thenReturn(null);

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenInvalidLoginPassword_thenStatus403( ) throws Exception {

        when( service.verifyLogin( Mockito.any()) ).thenReturn(null);

        String body = "{\"email\":\"cunha@ua.pt\", \"password\": \"----\"}";

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

        verify(service, times(1)).verifyLogin(Mockito.any());
    }

    @Test
    void whenInvalidLoginEmailAndPassword_thenStatus403( ) throws Exception {
        Client badClient = new Client(null, "----@ua.pt", "----", null, null, null);

        when( service.verifyLogin( Mockito.any()) ).thenReturn(null);

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(badClient)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

        verify(service, times(1)).verifyLogin(Mockito.any());
    }
}
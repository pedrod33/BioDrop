package pt.deliveries.deliveries_engine.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Service.CourierService;
import pt.deliveries.deliveries_engine.controller.CourierController;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierController.class)
class CourierControllerTest {

    private Courier courier;

    @Autowired
    MockMvc mvc;

    @MockBean
    CourierService service;

    @BeforeEach
    void setUp(){
        courier = new Courier("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, new Supervisor("Carlos","12345678"));
    }
    //Register Tests
    @Test
    void registerCredentialsSuccessful() throws Exception {
        when(service.login(courier)).thenReturn(Optional.of(courier));
        mvc.perform((post("/deliveries/api")
            .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(courier))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("Marco Alves")));
    }

    @Test
    void registerExistingEmail(){
    }

    @Test
    void registerInvalidCredentialsError() {
    }

    //Login Tests
    @Test
    void LoginValidCredentialsSuccessful() {
    }
}

package pt.deliveries.deliveries_engine.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Service.CourierService;
import pt.deliveries.deliveries_engine.controller.CourierController;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierController.class)
class CourierControllerTest {

    private Logger logger = Logger.getLogger(CourierController.class.getName());
    private Courier courier;

    @Autowired
    MockMvc mvc;

    @MockBean
    CourierService service;

    @BeforeEach
    void setUp(){
        courier = new Courier("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, new Supervisor("Carlos","12345678"), new Vehicle("car"));
    }
    //Register Tests
    @Test
    void registerCredentialsSuccessful() throws Exception {
        when(service.exists(courier)).thenReturn(false);
        when(service.save(Mockito.any())).thenReturn(courier);
        mvc.perform((post("/deliveries-api/register")
            .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(courier))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(courier.getName())));
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void registerExistingEmail() throws Exception{
        when(service.exists(Mockito.any())).thenReturn(true);
        mvc.perform((post("/deliveries-api/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(courier))))
                .andExpect(status().isImUsed())
                .andExpect(jsonPath("$.name", is("Marco Alves")));
        verify(service, times(1)).exists(Mockito.any());
    }

    @Test
    void registerInvalidCredentialsError() {
    }

    //Login Tests
    @Test
    void LoginValidCredentialsSuccessful() throws Exception{
        when(service.exists(Mockito.any())).thenReturn(true);
        when(service.verifyLogin(Mockito.any())).thenReturn(courier);
        mvc.perform((post("/deliveries-api/login")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(courier))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Marco Alves")));
        verify(service, times(1)).exists(Mockito.any());
    }

    @Test
    void LoginPasswordDoesNotMatch() throws Exception{
        when(service.exists(Mockito.any())).thenReturn(true);
        when(service.verifyLogin(Mockito.any())).thenReturn(null);
        mvc.perform((post("/deliveries-api/login")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(courier))))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isUnauthorized());
        verify(service, times(1)).exists(Mockito.any());
    }
    @Test
    void LoginEmailDoesNotMatch() throws Exception{
        when(service.exists(Mockito.any())).thenReturn(false);
        when(service.verifyLogin(Mockito.any())).thenReturn(Mockito.any());
        mvc.perform((post("/deliveries-api/login")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(courier))))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isUnauthorized());
        verify(service, times(1)).exists(Mockito.any());
    }
}

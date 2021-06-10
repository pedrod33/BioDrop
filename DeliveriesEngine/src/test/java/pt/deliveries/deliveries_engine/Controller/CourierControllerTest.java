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
import pt.deliveries.deliveries_engine.Pojo.LoginCourierPojo;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;
import pt.deliveries.deliveries_engine.Service.CourierServiceImpl;
import pt.deliveries.deliveries_engine.controller.CourierController;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierController.class)
class CourierControllerTest {

    private Logger logger = Logger.getLogger(CourierController.class.getName());

    @Autowired
    MockMvc mvc;

    @MockBean
    CourierServiceImpl service;


    private Courier courier;
    private RegisterCourierPojo rcp1;
    private Vehicle v1;
    private LoginCourierPojo lcp1;
    @BeforeEach
    void setUp(){
        rcp1 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L);
        v1 = new Vehicle("car");
        v1.setId(1L);
        courier = new Courier("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, new Supervisor("carlos@gmail.com","12345678", "Carlos"), new Vehicle("car"));
        lcp1 = new LoginCourierPojo("marcoA@gmail.com", "12345678");
    }
    //Register Tests
    @Test
    void registerCredentialsSuccessful() throws Exception {
        when(service.exists(rcp1)).thenReturn(false);
        when(service.save(Mockito.any())).thenReturn(courier);
        mvc.perform((post("/deliveries-api/courier/register")
            .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(rcp1))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(courier.getName())));
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void registerExistingEmail() throws Exception{
        when(service.exists(Mockito.any())).thenReturn(true);
        mvc.perform((post("/deliveries-api/courier/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(rcp1))))
                .andExpect(status().isImUsed())
                .andExpect(jsonPath("$").doesNotExist());
        verify(service, times(1)).exists(Mockito.any());
    }

    //Login Tests
    @Test
    void LoginValidCredentialsSuccessful() throws Exception{
        when(service.emailExists(Mockito.any())).thenReturn(true);
        when(service.verifyLogin(Mockito.any())).thenReturn(courier);
        mvc.perform((post("/deliveries-api/courier/login")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(lcp1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Marco Alves")));
        verify(service, times(1)).emailExists(Mockito.any());
    }

    @Test
    void LoginPasswordDoesNotMatch() throws Exception{
        when(service.emailExists(Mockito.any())).thenReturn(true);
        when(service.verifyLogin(Mockito.any())).thenReturn(null);
        mvc.perform((post("/deliveries-api/courier/login")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(lcp1))))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isUnauthorized());
        verify(service, times(1)).emailExists(Mockito.any());
    }
    @Test
    void LoginEmailDoesNotMatch() throws Exception{
        when(service.emailExists(Mockito.any())).thenReturn(false);
        when(service.verifyLogin(Mockito.any())).thenReturn(Mockito.any());
        mvc.perform((post("/deliveries-api/courier/login")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(lcp1))))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isUnauthorized());
        verify(service, times(1)).emailExists(Mockito.any());
    }
}

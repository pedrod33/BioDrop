package pt.deliveries.deliveries_engine.Controller;

import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Service.CourierServiceImpl;
import pt.deliveries.deliveries_engine.Service.SupervisorServiceImpl;
import pt.deliveries.deliveries_engine.controller.CourierController;
import pt.deliveries.deliveries_engine.controller.SupervisorController;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupervisorController.class)
public class SupervisorControllerTest {

    private Logger logger = Logger.getLogger(CourierController.class.getName());
    private Supervisor supervisor;

    @Autowired
    MockMvc mvc;

    @MockBean
    SupervisorServiceImpl service;

    @BeforeEach
    void setUp(){
        supervisor = new Supervisor("supervisor@gmail.com", "12345678", "Domingos Manuel");
    }

    @Test
    void registerUsedCredentialsSuccessful() throws Exception {
        when(service.exists(supervisor)).thenReturn(false);
        when(service.create(Mockito.any())).thenReturn(supervisor);
        mvc.perform((post("/deliveries-api/supervisor/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(supervisor))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(supervisor.getName())));
        verify(service, times(1)).create(Mockito.any());
    }
    @Test
    void registerExistingCredentialsUnsuccessful() throws Exception {
        when(service.exists(Mockito.any())).thenReturn(true);
        mvc.perform((post("/deliveries-api/supervisor/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(supervisor))))
                .andExpect(status().isImUsed())
                .andExpect(jsonPath("$.name",is(supervisor.getName())));
        verify(service, times(0)).create(Mockito.any());
    }
}

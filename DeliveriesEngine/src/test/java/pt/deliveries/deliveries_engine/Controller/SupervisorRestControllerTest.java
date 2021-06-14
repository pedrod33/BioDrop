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
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;
import pt.deliveries.deliveries_engine.Service.SupervisorServiceImpl;
import pt.deliveries.deliveries_engine.controller.CourierRestController;
import pt.deliveries.deliveries_engine.controller.SupervisorRestController;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupervisorRestController.class)
public class SupervisorRestControllerTest {

    private Logger logger = Logger.getLogger(CourierRestController.class.getName());
    private Supervisor supervisor;
    private RegisterSupervisorPojo supervisorPojo;

    @Autowired
    MockMvc mvc;

    @MockBean
    SupervisorServiceImpl service;

    @BeforeEach
    void setUp(){
        supervisorPojo = new RegisterSupervisorPojo("supervisorPojo@gmail.com", "12345678", "Semedo Manuel");
        supervisor = new Supervisor("supervisor@gmail.com", "12345678", "Domingos Manuel");
    }

    @Test
    void registerUsedCredentialsSuccessful() throws Exception {
        when(service.existsRegister(supervisorPojo)).thenReturn(false);
        when(service.create(Mockito.any())).thenReturn(supervisor);
        mvc.perform((post("/deliveries-api/supervisor/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(supervisor))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(supervisor.getName())));
        verify(service, times(1)).create(Mockito.any());
    }
    @Test
    void registerExistingCredentialsUnsuccessful() throws Exception {
        when(service.existsRegister(Mockito.any())).thenReturn(true);
        mvc.perform((post("/deliveries-api/supervisor/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(supervisor))))
                .andExpect(status().isImUsed())
                .andExpect(jsonPath("$").doesNotExist());
        verify(service, times(0)).create(Mockito.any());
    }
}

package pt.deliveries.deliveries_engine.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Service.VehicleServiceImpl;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleRestController.class)
public class VehicleRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    VehicleServiceImpl service;

    @Test
    void createVehicle() throws Exception {
        Vehicle vehicle = new Vehicle("car");
        when(service.exists(vehicle.getType())).thenReturn(false);
        vehicle.setId(1L);
        when(service.create(Mockito.any())).thenReturn(vehicle);
        mvc.perform((post("/deliveries-api/vehicle/create/?type=car")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(vehicle))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type", is(vehicle.getType())));
        verify(service, times(1)).create(Mockito.any());
    }
}

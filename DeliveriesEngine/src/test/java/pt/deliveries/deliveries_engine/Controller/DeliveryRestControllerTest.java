package pt.deliveries.deliveries_engine.Controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Exception.CourierEmailAndPasswordDoNotMatchException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeDoesNotExistException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Service.DeliveryServiceImpl;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierRestController.class)
public class DeliveryRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    DeliveryServiceImpl service;

    private Delivery delivery;

    @BeforeEach
    public void setUp(){
        Courier courier = new Courier("Marco Alves",
            "marcoA@gmail.com","12345678","M",931231233L,
            new Supervisor("carlos@gmail.com","12345678", "Carlos"),
            new Vehicle("car")
        );
        courier.setId(1L);
        Vehicle vehicle = new Vehicle("car");
        delivery = new Delivery(courier, 1L);
    }

    @Test
    public void createDeliveryInvalidVehicleType() throws Exception {
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 1L);
        doThrow(VehicleTypeDoesNotExistException.class).when(service).create(Mockito.any());

        mvc.perform((post("/deliveries-api/deliveries/create")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cdp))))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof VehicleTypeDoesNotExistException));

        verify(service, times(1)).service(Mockito.any());
    }
}

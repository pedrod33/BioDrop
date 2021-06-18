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
import pt.deliveries.deliveries_engine.Exception.CourierIdDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.OrderIdDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeDoesNotExistException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Service.DeliveryServiceImpl;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryRestController.class)
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
        vehicle.setId(1L);
        delivery = new Delivery(courier, 1L);
    }

    //create

    @Test
    public void createDeliveryInvalidVehicleType() throws Exception {
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 2L);
        doThrow(VehicleTypeDoesNotExistException.class).when(service).canCreate(Mockito.any());

        mvc.perform((post("/deliveries-api/deliveries/create")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cdp))))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof VehicleTypeDoesNotExistException));

        verify(service, times(1)).canCreate(Mockito.any());
    }

    @Test
    public void createDeliveryValidCredentials() throws Exception {
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 1L);
        when(service.canCreate(Mockito.any())).thenReturn(true);
        when(service.create(Mockito.any())).thenReturn(delivery);

        mvc.perform((post("/deliveries-api/deliveries/create")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cdp))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.courier.name", is("Marco Alves")));
    }

    @Test
    public void createDeliveryInvalidOrder() throws Exception{
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L, 1L);
        doThrow(OrderIdDoesNotExistException.class).when(service).canCreate(Mockito.any());

        mvc.perform((post("/deliveries-api/deliveries/create")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cdp))))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderIdDoesNotExistException));

        verify(service, times(1)).canCreate(Mockito.any());
    }

    @Test
    public void createDeliveryInvalidCourier() throws Exception{
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2L, 1L, 1L);
        doThrow(CourierIdDoesNotExistException.class).when(service).canCreate(Mockito.any());

        mvc.perform((post("/deliveries-api/deliveries/create")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cdp))))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CourierIdDoesNotExistException));

        verify(service, times(1)).canCreate(Mockito.any());
    }
}

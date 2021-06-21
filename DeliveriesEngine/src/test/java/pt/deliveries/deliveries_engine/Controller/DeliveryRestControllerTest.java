package pt.deliveries.deliveries_engine.Controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Exception.*;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Service.DeliveryServiceImpl;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    Vehicle vehicle;
    Courier courier;

    @BeforeEach
    void setUp(){
        courier = new Courier("Marco Alves",
            "marcoA@gmail.com","12345678","M",931231233L,
            new Supervisor("carlos@gmail.com","12345678", "Carlos"),
            vehicle
        );
        courier.setId(1L);
        vehicle = new Vehicle("car");
        vehicle.setId(1L);
        delivery = new Delivery(courier, 1L,3,40,5,60, 1L);
    }

    //create
    @Test
    void createDeliveryNoCouriersAvailable() throws Exception {
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(3, 40, 5, 60);
        doThrow(CourierTakenException.class).when(service).canCreate(eq(1L), eq(1L), Mockito.any());

        mvc.perform((post("/deliveries-api/deliveries/create")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cdp))))
                .andExpect(status().isImUsed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CourierTakenException));

        verify(service, times(1)).canCreate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    }


    //find by order id
    @Test
    void findByOrderIdValidId() throws Exception{
        when(service.getDeliveryByOrderId(1L)).thenReturn(delivery);

        mvc.perform((get("/deliveries-api/deliveries/findByOrder_id?order_id=1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.courier.name", is("Marco Alves")));
    }

    @Test
    void findByOrderIdInvalidId() throws Exception{
        doThrow(OrderIdDoesNotExistException.class).when(service).getDeliveryByOrderId(2L);

        mvc.perform((get("/deliveries-api/deliveries/findByOrder_id?order_id=2")))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderIdDoesNotExistException));
    }

    //find all
    @Test
    void findAll_returnsAllElements() throws Exception{
        Vehicle v1 = new Vehicle("bike");
        v1.setId(2L);
        Delivery delivery1= new Delivery();
        delivery1.setOrder_id(2L);
        delivery1.setCourier(courier);
        List<Delivery> allDeliveries = new ArrayList<>(Arrays.asList(delivery, delivery1));
        when(service.findAllDeliveries()).thenReturn(allDeliveries);
        mvc.perform((get("/deliveries-api/deliveries/findAll")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[1].courier.vehicle.type", is("bike")))
            .andExpect(jsonPath("$.[0].courier.vehicle.type", is("car")));
    }

    @Test
    void findAll_returnsEmpty() throws Exception{
        List<Delivery> allDeliveries = new ArrayList<>();
        when(service.findAllDeliveries()).thenReturn(allDeliveries);
        mvc.perform((get("/deliveries-api/deliveries/findAll")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void findByValidId_returnsDelivery() throws Exception{
        when(service.findDeliveryById(1L)).thenReturn(delivery);
        mvc.perform((get("/deliveries-api/deliveries/findById?id=1")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.courier.id", is(1)));
    }

    @Test
    void findByInvalidId_returnsException() throws Exception{
        doThrow(DeliveryDoesNotExistException.class).when(service).findDeliveryById(2L);

        mvc.perform((get("/deliveries-api/deliveries/findById?id=2")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }
}

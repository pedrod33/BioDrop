package pt.deliveries.deliveries_engine.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.*;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    Logger logger = Logger.getLogger(DeliveryServiceImpl.class.getName());

    @Mock(lenient = true)
    private DeliveryRepository deliveryRepository;

    @Mock(lenient = true)
    private VehicleRepository vehicleRepository;

    @Mock(lenient = true)
    private CourierRepository courierRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    Courier c1;
    private Vehicle v1;
    private Vehicle v2;
    private Delivery delivery;
    @BeforeEach
    void setUp(){
        v1 = new Vehicle("car");
        v1.setId(1L);
        v2 = new Vehicle("bike");
        v2.setId(2L);
        c1 = new Courier("Marco Alves", "marcoA@gmail.com", "12345678", "M",
            931231233L, new Supervisor("Carlos", "carlos@gmail.com","12345678")
            , v2
        );
        c1.setId(1L);
        delivery = new Delivery(c1, 1L,3,40,5,60, 1L);
        //vehicle exists
        when(vehicleRepository.findById(1L)).thenReturn(v1);
        when(vehicleRepository.findById(2L)).thenReturn(v2);

        //courier exists

        //order already assigned to delivery
        when(deliveryRepository.findDeliveryByOrderId(Mockito.anyLong())).thenReturn(null);
        when(deliveryRepository.findDeliveryByOrderId(1L)).thenReturn(delivery);

        //order exists
        when(deliveryRepository.existsOrderFromDeliveryById(Mockito.anyLong())).thenReturn(false);
        when(deliveryRepository.existsOrderFromDeliveryById(2L)).thenReturn(true);


    }

    //create
    @Test
    @Disabled
    void whenVehicleInValid_thenReturnFalse_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2, 2, 2, 3);
        assertThrows(VehicleTypeDoesNotExistException.class, () -> deliveryService.create(1L, 1L, cdp));
    }

    @Test
    @Disabled
    void whenOrderInValid_thenReturnFalse_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1, 1, 2, 2);
        assertThrows(OrderIdAlreadyUsedException.class, () -> deliveryService.create(1L, 1L, cdp));

    }

    @Test
    @Disabled
    void whenCourierInValid_thenReturnFalse_create(){
        when(courierRepository.findCouriersByStatus(Mockito.anyInt())).thenReturn(new ArrayList<>());

        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1,1,2, 1);
        assertThrows(CourierTakenException.class, () -> deliveryService.create(1L, 1L, cdp));

    }

    @Test
    @Disabled
    void whenCourierInValidNoVehicle_thenReturnException_create(){
        when(courierRepository.findCouriersByStatus(Mockito.anyInt())).thenReturn(new ArrayList<>());
        CreateDeliveryPojo cdp = new CreateDeliveryPojo( 1,1,4,2);
        assertThrows(CourierTakenException.class, () -> deliveryService.create(1L, 1L, cdp));
    }

    @Test
    @Disabled
    void whenOrderInValidNoVehicle_thenReturnException_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo( 1, 1,1,1);
        assertThrows(OrderIdAlreadyUsedException.class, () -> deliveryService.create(1L, 2L, cdp));
    }

    @Test
    void whenOrderValidNoVehicle_thenReturnWithCourierVehicle_create(){
        when(courierRepository.findCouriersByStatus(0)).thenReturn(new ArrayList<>(Arrays.asList(c1)));
        when(deliveryRepository.save(Mockito.any())).thenReturn(delivery);
        CreateDeliveryPojo cdp = new CreateDeliveryPojo( 1,1,1,2);
        delivery.setOrder_id(2L);
        Delivery delivery_res = deliveryService.create(1L, 1L, cdp);
        assertThat(delivery_res.getOrder_id()).isEqualTo(2L);
    }

    @Test
    void whenOrderValidWithVehicle_thenReturnVehicle_create(){
        when(courierRepository.findCouriersByStatus(0)).thenReturn(new ArrayList<>(Arrays.asList(c1)));
        when(deliveryRepository.save(Mockito.any())).thenReturn(delivery);
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1,1, 2, 2);
        delivery.setOrder_id(2L);
        Delivery delivery_res = deliveryService.create(1L,1L,cdp);
        assertThat(delivery_res.getOrder_id()).isEqualTo(2L);
    }

    //find delivery by order id
    @Test
    void whenOrderIdIsValid_thenReturnDelivery_findByOrderId(){
        Delivery delivery = deliveryService.getDeliveryByOrderId(1L);
        assertThat(delivery.getOrder_id()).isEqualTo(1L);
    }

    @Test
    void whenOrderIdIsInvalid_thenReturnDelivery_findByOrderId(){
        assertThrows(OrderIdDoesNotExistException.class, () -> deliveryService.getDeliveryByOrderId(2L));
    }

    //find all
    @Test
    void whenFindAll_thenReturnAllElements(){
        Vehicle v1 = new Vehicle("bike");
        v1.setId(2L);
        Delivery delivery1= new Delivery();
        delivery1.setOrder_id(2L);
        delivery1.setCourier(c1);
        List<Delivery> allDeliveries = new ArrayList<>(Arrays.asList(delivery, delivery1));
        when(deliveryRepository.findAll()).thenReturn(allDeliveries);
        List<Delivery> res = deliveryRepository.findAll();
        assertThat(res.size()).isEqualTo(2);
        assertThat(res.get(0).getCourier().getVehicle().getType()).isEqualTo("bike");
        assertThat(res.get(1).getCourier().getVehicle().getType()).isEqualTo("bike");
    }

    @Test
    void whenFindAll_thenReturnEmpty(){
        Vehicle v1 = new Vehicle("bike");
        v1.setId(2L);
        Delivery delivery1= new Delivery();
        delivery1.setOrder_id(2L);
        delivery1.setCourier(c1);
        List<Delivery> allDeliveries = new ArrayList<>(Arrays.asList(delivery, delivery1));
        when(deliveryRepository.findAll()).thenReturn(allDeliveries);
        List<Delivery> res = deliveryRepository.findAll();
        assertThat(res.size()).isEqualTo(2);
        assertThat(res.get(0).getCourier().getVehicle().getType()).isEqualTo("bike");
        assertThat(res.get(1).getCourier().getVehicle().getType()).isEqualTo("bike");
    }

    //findById
    @Test
    void whenFindByInvalidId_ReturnException(){
        assertThrows(DeliveryDoesNotExistException.class, () -> deliveryService.findDeliveryById(2L));
    }

    @Test
    void whenFindById_ReturnDelivery(){
        when(deliveryRepository.findById(1L)).thenReturn(delivery);
        Delivery delivery_result = deliveryService.findDeliveryById(1L);
        assertThat(delivery_result.getOrder_id()).isEqualTo(delivery.getOrder_id());
        assertThat(delivery_result.getCourier().getId()).isEqualTo(delivery.getCourier().getId());
    }
}

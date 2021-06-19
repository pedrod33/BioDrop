package pt.deliveries.deliveries_engine.Service;


import org.junit.jupiter.api.BeforeEach;
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
    private Delivery delivery;
    @BeforeEach
    public void setUp(){
        v1 = new Vehicle("car");
        v1.setId(1L);
        c1 = new Courier("Marco Alves", "marcoA@gmail.com", "12345678", "M",
            931231233L, new Supervisor("Carlos", "carlos@gmail.com","12345678")
            , new Vehicle("car")
        );
        c1.setId(1L);
        delivery = new Delivery(c1, 1L);
        //vehicle exists
        when(vehicleRepository.findById(1L)).thenReturn(v1);

        //courier exists
        when(courierRepository.findById(Mockito.anyLong())).thenReturn(null);
        when(courierRepository.findById(1L)).thenReturn(c1);

        //order already assgined to delivery
        when(deliveryRepository.findDeliveryByOrderId(Mockito.anyLong())).thenReturn(null);
        when(deliveryRepository.findDeliveryByOrderId(1L)).thenReturn(delivery);

        //order exists
        when(deliveryRepository.existsOrderFromDeliveryById(Mockito.anyLong())).thenReturn(false);
        when(deliveryRepository.existsOrderFromDeliveryById(2L)).thenReturn(true);


    }

    //create
    @Test
    public void whenVehicleInValid_thenReturnFalse_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L, 2L);
        assertThrows(VehicleTypeDoesNotExistException.class, () -> deliveryService.create(cdp));
    }

    @Test
    public void whenOrderInValid_thenReturnFalse_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 1L);
        assertThrows(OrderIdAlreadyUsedException.class, () -> deliveryService.create(cdp));

    }

    @Test
    public void whenCourierInValid_thenReturnFalse_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2L, 2L, 1L);
        assertThrows(CourierIdDoesNotExistException.class, () -> deliveryService.create(cdp));

    }

    @Test
    public void whenCourierInValidNoVehicle_thenReturnException_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2L, 2L);
        assertThrows(CourierIdDoesNotExistException.class, () -> deliveryService.create(cdp));
    }

    @Test
    public void whenOrderInValidNoVehicle_thenReturnException_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L);
        assertThrows(OrderIdAlreadyUsedException.class, () -> deliveryService.create(cdp));
    }

    @Test
    public void whenOrderValidNoVehicle_thenReturnWithCourierVehicle_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L);
        Delivery delivery = deliveryService.create(cdp);
        assertThat(delivery.getOrder_id()).isEqualTo(2L);
        assertThat(delivery.getVehicle().getId()).isEqualTo(c1.getVehicle().getId());
    }

    @Test
    public void whenOrderValidWithVehicle_thenReturnVehicle_create(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L, 1L);
        Delivery delivery = deliveryService.create(cdp);
        assertThat(delivery.getOrder_id()).isEqualTo(2L);
        assertThat(delivery.getVehicle().getId()).isEqualTo(cdp.getVehicle_id());
    }

    //find delivery by order id
    @Test
    public void whenOrderIdIsValid_thenReturnDelivery_findByOrderId(){
        Delivery delivery = deliveryService.getDeliveryByOrderId(1L);
        assertThat(delivery.getOrder_id()).isEqualTo(1L);
    }

    @Test
    public void whenOrderIdIsInvalid_thenReturnDelivery_findByOrderId(){
        assertThrows(OrderIdDoesNotExistException.class, () -> deliveryService.getDeliveryByOrderId(2L));
    }

    //find all
    @Test
    public void whenFindAll_thenReturnAllElements(){
        delivery.setVehicle(delivery.getCourier().getVehicle());
        Vehicle v1 = new Vehicle("bike");
        v1.setId(2L);
        Delivery delivery1= new Delivery();
        delivery1.setOrder_id(2L);
        delivery1.setVehicle(v1);
        delivery1.setCourier(c1);
        List<Delivery> allDeliveries = new ArrayList<>(Arrays.asList(delivery, delivery1));
        when(deliveryRepository.findAll()).thenReturn(allDeliveries);
        List<Delivery> res = deliveryRepository.findAll();
        assertThat(res.size()).isEqualTo(2);
        assertThat(res.get(0).getVehicle().getType()).isEqualTo("car");
        assertThat(res.get(1).getVehicle().getType()).isEqualTo("bike");
    }

    @Test
    public void whenFindAll_thenReturnEmpty(){
        delivery.setVehicle(delivery.getCourier().getVehicle());
        Vehicle v1 = new Vehicle("bike");
        v1.setId(2L);
        Delivery delivery1= new Delivery();
        delivery1.setOrder_id(2L);
        delivery1.setVehicle(v1);
        delivery1.setCourier(c1);
        List<Delivery> allDeliveries = new ArrayList<>(Arrays.asList(delivery, delivery1));
        when(deliveryRepository.findAll()).thenReturn(allDeliveries);
        List<Delivery> res = deliveryRepository.findAll();
        assertThat(res.size()).isEqualTo(2);
        assertThat(res.get(0).getVehicle().getType()).isEqualTo("car");
        assertThat(res.get(1).getVehicle().getType()).isEqualTo("bike");
    }

    //findById
    @Test
    public void whenFindByInvalidId_ReturnException(){
        assertThrows(DeliveryDoesNotExistException.class, () -> deliveryService.findDeliveryById(2L));
    }

    @Test
    public void whenFindById_ReturnDelivery(){
        when(deliveryRepository.findById(1L)).thenReturn(delivery);
        Delivery delivery_result = deliveryService.findDeliveryById(1L);
        assertThat(delivery_result.getOrder_id()).isEqualTo(delivery.getOrder_id());
        assertThat(delivery_result.getCourier().getId()).isEqualTo(delivery.getCourier().getId());
    }
}

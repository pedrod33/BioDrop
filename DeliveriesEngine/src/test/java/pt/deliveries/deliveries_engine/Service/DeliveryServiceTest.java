package pt.deliveries.deliveries_engine.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.CourierIdDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.OrderIdAlreadyUsedException;
import pt.deliveries.deliveries_engine.Exception.SupervisorEmailIsUsedException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeDoesNotExistException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

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

        //courier exists
        when(courierRepository.findById(Mockito.anyLong())).thenReturn(null);
        when(courierRepository.findById(1L)).thenReturn(c1);

        //order already assgined to delivery
        when(deliveryRepository.findByOrder_id(Mockito.anyLong())).thenReturn(null);
        when(deliveryRepository.findByOrder_id(1L)).thenReturn(delivery);

        //order exists
        when(deliveryRepository.existsOrderFromDeliveryById(Mockito.anyLong())).thenReturn(false);
        when(deliveryRepository.existsOrderFromDeliveryById(2L)).thenReturn(true);


    }

    //canCreate() vehicle
    @Test
    public void whenCredentialsValid_thenReturnTrue_canCreate(){
        when(vehicleRepository.findById(1L)).thenReturn(v1);

        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L, 1L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isTrue();
    }

    @Test
    public void whenVehicleInValid_thenReturnFalse_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L, 2L);
        assertThrows(VehicleTypeDoesNotExistException.class, () -> deliveryService.canCreate(cdp));
    }

    @Test
    public void whenOrderInValid_thenReturnFalse_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 1L);
        assertThrows(OrderIdAlreadyUsedException.class, () -> deliveryService.canCreate(cdp));

    }

    @Test
    public void whenCourierInValid_thenReturnFalse_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2L, 2L, 1L);
        assertThrows(CourierIdDoesNotExistException.class, () -> deliveryService.canCreate(cdp));

    }

    //canCreate() no vehicle
    @Test
    public void whenCredentialsValidNoVehicle_thenReturnTrue_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isTrue();
    }

    @Test
    public void whenCourierInValidNoVehicle_thenReturnException_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2L, 2L);
        assertThrows(CourierIdDoesNotExistException.class, () -> deliveryService.canCreate(cdp));
    }

    @Test
    public void whenOrderInValidNoVehicle_thenReturnException_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L);
        assertThrows(OrderIdAlreadyUsedException.class, () -> deliveryService.canCreate(cdp));
    }
}

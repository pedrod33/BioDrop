package pt.deliveries.deliveries_engine.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.CourierEmailOrPhoneNumberInUseException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

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
        when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(null);
        when(vehicleRepository.findById(1L)).thenReturn(v1);
        when(courierRepository.findById(Mockito.anyLong())).thenReturn(null);
        when(courierRepository.findById(1L)).thenReturn(c1);
        when(deliveryRepository.findByOrder_id(Mockito.anyLong())).thenReturn(null);
        when(deliveryRepository.findByOrder_id(1L)).thenReturn(delivery);
    }

    //canCreate() vehicle
    @Test
    public void whenCredentialsValid_thenReturnTrue_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 1L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isTrue();
    }

    @Test
    public void whenVehicleInValid_thenReturnFalse_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 2L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isFalse();
    }

    @Test
    public void whenOrderInValid_thenReturnFalse_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 2L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isFalse();
    }

    @Test
    public void whenCourierInValid_thenReturnFalse_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L, 2L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isFalse();//exception
    }

    //canCreate() no vehicle
    @Test
    public void whenCredentialsValidNoVehicle_thenReturnTrue_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 1L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isTrue();
    }

    @Test
    public void whenCourierInValidNoVehicle_thenReturnException_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(2L, 1L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isFalse();//exception
    }

    @Test
    public void whenOrderInValidNoVehicle_thenReturnException_canCreate(){
        CreateDeliveryPojo cdp = new CreateDeliveryPojo(1L, 2L);
        boolean res_delivery = deliveryService.canCreate(cdp);
        assertThat(res_delivery).isFalse();//exception
    }
}

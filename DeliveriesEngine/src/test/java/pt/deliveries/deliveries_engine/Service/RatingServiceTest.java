package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock(lenient = true)
    private RatingRepository ratingRepository;

    @Mock(lenient = true)
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

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
        delivery
    }

    @Test
    public void firstTest(){

    }
}

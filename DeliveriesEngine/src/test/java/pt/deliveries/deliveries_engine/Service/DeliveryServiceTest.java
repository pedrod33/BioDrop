package pt.deliveries.deliveries_engine.Service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock(lenient = true)
    private DeliveryRepository deliveryRepository;


    @InjectMocks
    private DeliveryServiceImpl deliveryService;


}

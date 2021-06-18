package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;

@Service
@Transactional
public class DeliveryServiceImpl {

    @Autowired
    private DeliveryRepository deliveryRepository;
}

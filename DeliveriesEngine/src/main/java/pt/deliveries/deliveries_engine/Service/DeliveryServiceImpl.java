package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;

@Service
@Transactional
public class DeliveryServiceImpl {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery create(CreateDeliveryPojo deliveryPojo) {
        return null;
    }

    public boolean canCreate(CreateDeliveryPojo createPojo) {
        return false;
    }
}

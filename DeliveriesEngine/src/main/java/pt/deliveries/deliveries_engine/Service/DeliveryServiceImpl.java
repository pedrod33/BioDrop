package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.CourierIdDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.OrderIdAlreadyUsedException;
import pt.deliveries.deliveries_engine.Exception.OrderIdDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeDoesNotExistException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class DeliveryServiceImpl {

    Logger logger = Logger.getLogger(DeliveryServiceImpl.class.getName());
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CourierRepository courierRepository;

    public Delivery create(CreateDeliveryPojo deliveryPojo) {
        Delivery delivery = new Delivery();
        this.canCreate(deliveryPojo);
        Courier courier = courierRepository.findById(deliveryPojo.getCourier_id().longValue());
        delivery.setCourier(courier);
        if(deliveryPojo.getVehicle_id()==null){ delivery.setVehicle(courier.getVehicle()); }
        else{ delivery.setVehicle(vehicleRepository.findById(deliveryPojo.getVehicle_id().longValue())); }
        delivery.setOrder_id(deliveryPojo.getOrder_id());
        return delivery;
    }

    public boolean canCreate(CreateDeliveryPojo createPojo) {

        if(createPojo.getVehicle_id()!=null
            && vehicleRepository.findById(createPojo.getVehicle_id().longValue())==null){
            throw new VehicleTypeDoesNotExistException("This vehicle type does not exist!");
        }

        if(deliveryRepository.findByOrder_id(createPojo.getOrder_id())!=null){
            throw new OrderIdAlreadyUsedException("This order id was already used on another delivery!");
        }
        if(!deliveryRepository.existsOrderFromDeliveryById(createPojo.getOrder_id())){
            throw new OrderIdDoesNotExistException("There is no order with these values!");
        }
        if(courierRepository.findById(createPojo.getCourier_id().longValue())==null){
            throw new CourierIdDoesNotExistException("The courier with this ID does not exist");
        }
        return true;
    }
}

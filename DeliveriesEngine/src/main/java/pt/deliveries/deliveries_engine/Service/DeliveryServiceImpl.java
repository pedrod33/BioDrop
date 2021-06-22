package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.*;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.List;
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
        List<Courier> availableCouriers = courierRepository.findCouriersByStatus(0);
        Courier selected_courier = availableCouriers.get(0);
        delivery.setCourier(selected_courier);
        if(deliveryPojo.getVehicle_id()==-1){
            logger.log(Level.INFO, "was null");
            logger.log(Level.INFO, selected_courier.getVehicle().toString());
            delivery.setVehicle(selected_courier.getVehicle()); }
        else{
            logger.log(Level.INFO, "was not null");
            delivery.setVehicle(vehicleRepository.findById(deliveryPojo.getVehicle_id())); }
        delivery.setOrder_id(deliveryPojo.getOrder_id());
        selected_courier.setStatus(1);
        courierRepository.save(selected_courier);
        return deliveryRepository.save(delivery);
    }

    public boolean canCreate(CreateDeliveryPojo createPojo) {

        logger.log(Level.INFO, String.valueOf(vehicleRepository.findById(createPojo.getVehicle_id())));
        if(createPojo.getVehicle_id()!=-1
            && vehicleRepository.findById(createPojo.getVehicle_id())==null){
            throw new VehicleTypeDoesNotExistException("This vehicle type does not exist!");
        }

        if(deliveryRepository.findDeliveryByOrderId(createPojo.getOrder_id())!=null){
            throw new OrderIdAlreadyUsedException("This order id was already used on another delivery!");
        }
        if(!deliveryRepository.existsOrderFromDeliveryById(createPojo.getOrder_id())){
            throw new OrderIdDoesNotExistException("There is no order with these values!");
        }
        List<Courier> allAvailable = courierRepository.findCouriersByStatus(0);
        if(allAvailable.size()==0){
            throw new CourierTakenException("No Courier Available Right Now");
        }
        return true;
    }

    public Delivery getDeliveryByOrderId(long order_id) {
        Delivery delivery = deliveryRepository.findDeliveryByOrderId(order_id);
        if(delivery==null){
            throw new OrderIdDoesNotExistException("There is no assigned order with these values!");
        }
        return delivery;
    }

    public List<Delivery> findAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery findDeliveryById(long id) {
        Delivery delivery = deliveryRepository.findById(id);
        if(delivery==null){
            throw new DeliveryDoesNotExistException("There is no delivery with this information");
        }
        return delivery;
    }
}

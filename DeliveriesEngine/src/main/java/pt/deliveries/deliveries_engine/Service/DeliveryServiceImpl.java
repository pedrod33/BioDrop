package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.*;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Repository.BusinessInitiativeRepository;
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
    private final int PENDING = 4;
    private final int ACCEPTED = 0;
    private final int PICKED_UP = 1;
    private final int DELIVERED = 2;

    Logger logger = Logger.getLogger(DeliveryServiceImpl.class.getName());
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    BusinessInitiativeRepository businessRepository;

    public Delivery create(long clientId, long orderId, CreateDeliveryPojo deliveryPojo) {
        Delivery delivery = new Delivery();
        List<Courier> availableCouriers = courierRepository.findCouriersByStatus(0);
        Courier selected_courier = availableCouriers.get(0);
        delivery.setCourier(selected_courier);
        delivery.setClientId(clientId);
        delivery.setOrder_id(orderId);
        delivery.setLatClient(delivery.getLatClient());
        delivery.setLongClient(delivery.getLongClient());
        delivery.setLatStore(delivery.getLatStore());
        delivery.setLongStore(delivery.getLongStore());
        selected_courier.setStatus(0);
        courierRepository.save(selected_courier);
        return deliveryRepository.save(delivery);
    }

    public boolean canCreate(long orderId, long clientId, CreateDeliveryPojo createPojo) {
        if(deliveryRepository.findDeliveryByOrderId(orderId)!=null){
            throw new OrderIdAlreadyUsedException("This order id was already used on another delivery!");
        }
        //if(!businessRepository.existsOrder(orderId)){
        //    throw new OrderIdDoesNotExistException("There is no order with these values!");
        //}
        //if(!businessRepository.existsClient(clientId)){
        //    throw new ClientDoesNotExistException("There is no client with these values!");
        //}
        List<Courier> allAvailable = courierRepository.findCouriersByStatus(0);
        if(allAvailable.isEmpty()){
            throw new CourierTakenException("No Courier Available Right Now");
        }
        return true;
    }

    public Delivery decline(long courierId, long orderId){
        return null;
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

    public Delivery findDeliveryByCourierIdToBeAccepted(long id){
        Courier courier = courierRepository.findById(id);
        courier.setStatus(1);
        courierRepository.save(courier);
        return deliveryRepository.findTopByCourier_IdAndStatus(id, PENDING);
    }

    //TODO:tests
    public Delivery updateDeliveryStatus(long orderId, long courierId) {
        Delivery delivery = deliveryRepository.findDeliveryByOrderId(orderId);
        if(delivery==null){
            throw new OrderIdDoesNotExistException("There is no order to be updated");
        }
        if (delivery.getStatus()==ACCEPTED) {
            delivery.setStatus(PICKED_UP);
        }
        else if(delivery.getStatus()==PICKED_UP) {
            delivery.setStatus(DELIVERED);
            Delivery saved = deliveryRepository.save(delivery);
            Courier c = courierRepository.findById(delivery.getCourier().getId().longValue());
            c.setStatus(0);
            courierRepository.save(c);
            return saved;
        }
        else if(delivery.getStatus()==PENDING){
            delivery.setStatus(ACCEPTED);
        }
        else{
            throw new DeliveryWithOrderIdHasBeenProcessedException("The status for this delivery cannot e updated!");
        }
        return deliveryRepository.save(delivery);
    }
}

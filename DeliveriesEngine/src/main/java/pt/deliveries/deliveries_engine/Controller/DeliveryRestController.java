package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Service.DeliveryServiceImpl;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/deliveries-api/deliveries")
public class DeliveryRestController {

    @Autowired
    private DeliveryServiceImpl deliveryService;

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestParam long clientId, @RequestParam long orderId, @RequestBody CreateDeliveryPojo createPojo){
        deliveryService.canCreate(clientId, orderId, createPojo);
        Delivery delivery = deliveryService.create(clientId, orderId, createPojo);
        return new ResponseEntity<>(delivery, HttpStatus.CREATED);
    }

    @GetMapping("/findByOrder_id")
    public ResponseEntity<Delivery> getDeliveryByOrderId(@RequestParam long order_id){
        return new ResponseEntity<>(deliveryService.getDeliveryByOrderId(order_id), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Delivery>> getAllDeliveries(){
        return new ResponseEntity<>(deliveryService.findAllDeliveries(), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Delivery> getDeliveryById(@RequestParam long id){
        return new ResponseEntity<>(deliveryService.findDeliveryById(id), HttpStatus.OK);
    }

    @PostMapping("/client/updateStatus")
    public ResponseEntity<Delivery> updateDeliveryByOrderId(@RequestParam long orderId, @RequestParam long courierId){
        return new ResponseEntity<>(deliveryService.updateDeliveryStatus(orderId, courierId), HttpStatus.OK);
    }

    @GetMapping("/findPendingOrders")
    public ResponseEntity<Delivery> getPendingDeliveries(@RequestParam long courierId){
        return new ResponseEntity<>(deliveryService.findDeliveryByCourierIdToBeAccepted(courierId), HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity<Delivery> accept(@RequestParam long courierId, @RequestParam long orderId){
        return new ResponseEntity<>(deliveryService.updateDeliveryStatus(orderId, courierId), HttpStatus.OK);
    }
    @PostMapping("/decline")
    public ResponseEntity<Delivery> decline(@RequestParam long courierId, @RequestParam long orderId){
        return new ResponseEntity<>(deliveryService.decline(courierId, orderId), HttpStatus.OK);
    }
}

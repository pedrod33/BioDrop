package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Service.DeliveryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/deliveries-api/deliveries")
public class DeliveryRestController {

    @Autowired
    private DeliveryServiceImpl deliveryService;

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody CreateDeliveryPojo createPojo){
        deliveryService.canCreate(createPojo);
        Delivery delivery = deliveryService.create(createPojo);
        return new ResponseEntity<>(delivery, HttpStatus.CREATED);
    }

    @GetMapping("findByOrder_id")
    public ResponseEntity<Delivery> getDeliveryByOrderId(@RequestParam long order_id){
        return new ResponseEntity<>(deliveryService.getDeliveryByOrderId(order_id), HttpStatus.OK);
    }

    @GetMapping("findAll")
    public ResponseEntity<List<Delivery>> getAllDeliveries(){
        return new ResponseEntity<>(deliveryService.findAllDeliveries(), HttpStatus.OK);
    }

    @GetMapping("findById")
    public ResponseEntity<Delivery> getDeliveryById(@RequestParam long id){
        return new ResponseEntity<>(deliveryService.findDeliveryById(id), HttpStatus.OK);
    }
}

package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Pojo.CreateDeliveryPojo;
import pt.deliveries.deliveries_engine.Service.DeliveryServiceImpl;

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



}

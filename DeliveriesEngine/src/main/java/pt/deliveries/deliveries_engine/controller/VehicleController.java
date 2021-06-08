package pt.deliveries.deliveries_engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Service.VehicleServiceImpl;

import java.util.logging.Logger;

@RestController
@RequestMapping("/deliveries-api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleServiceImpl service;

    private Logger logger = Logger.getLogger(CourierController.class.getName());

    @RequestMapping("/create")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle){
        if(service.exists(vehicle)){
            System.out.println("entra aqui null");
            return new ResponseEntity<>(null, HttpStatus.IM_USED);
        }
        Vehicle created = service.create(vehicle);
        System.out.println("entra aqui creates");
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

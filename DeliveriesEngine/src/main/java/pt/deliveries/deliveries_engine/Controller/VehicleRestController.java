package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Service.VehicleServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/deliveries-api/vehicle")
public class VehicleRestController {

    @Autowired
    private VehicleServiceImpl service;

    private Logger logger = Logger.getLogger(CourierRestController.class.getName());

    @PostMapping("/create")
    public ResponseEntity<Vehicle> createVehicle(@RequestParam String type){
        service.exists(type);
        Vehicle created = service.create(type);
        logger.log(Level.INFO, created.toString());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> findAllVehicles(){
        return new ResponseEntity<>(service.findAllVehicles(), HttpStatus.OK);
    }
}

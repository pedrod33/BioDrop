package pt.deliveries.deliveries_engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Service.CourierService;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/deliveries-api/courier")
public class CourierController {

    @Autowired
    private CourierService service;

    private Logger logger = Logger.getLogger(CourierController.class.getName());

    @RequestMapping("/register")
    public ResponseEntity<Courier> register(@RequestBody Courier courier) {
    if(service.exists(courier)){
        return new ResponseEntity<>(courier, HttpStatus.IM_USED);
    }
        Courier savedCourier = service.save(courier);
        return new ResponseEntity<>(savedCourier, HttpStatus.CREATED);
    }

    @RequestMapping("/login")
    public ResponseEntity<Courier> login(@RequestBody Courier courier) {
        if(service.exists(courier)){
            Courier verificationCourier = service.verifyLogin(courier);
            if(verificationCourier!=null){
                return new ResponseEntity<>(verificationCourier, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}

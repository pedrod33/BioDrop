package pt.deliveries.deliveries_engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Pojo.LoginCourierPojo;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;
import pt.deliveries.deliveries_engine.Service.CourierServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/deliveries-api/courier")
public class CourierController {

    Logger logger = Logger.getLogger(CourierController.class.getName());

    @Autowired
    private CourierServiceImpl service;

    @RequestMapping("/register")
    public ResponseEntity<Courier> register(@RequestBody RegisterCourierPojo courierPojo) {
    if(service.exists(courierPojo)){
        return new ResponseEntity<>(null, HttpStatus.IM_USED);
    }
        Courier savedCourier = service.save(courierPojo);
        return new ResponseEntity<>(savedCourier, HttpStatus.CREATED);
    }

    @RequestMapping("/login")
    public ResponseEntity<Courier> login(@RequestBody LoginCourierPojo loginCourierPojo) {
        if(service.emailExists(loginCourierPojo)){
            Courier verificationCourier = service.verifyLogin(loginCourierPojo);
            if(verificationCourier!=null){
                return new ResponseEntity<>(verificationCourier, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}

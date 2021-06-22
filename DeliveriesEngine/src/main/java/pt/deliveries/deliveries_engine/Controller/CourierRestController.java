package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Pojo.LoginCourierPojo;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;
import pt.deliveries.deliveries_engine.Service.CourierServiceImpl;

import java.util.logging.Logger;

@RestController
@RequestMapping("/deliveries-api/courier")
public class CourierRestController {

    Logger logger = Logger.getLogger(CourierRestController.class.getName());

    @Autowired
    private CourierServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<Courier> register(@RequestBody RegisterCourierPojo courierPojo) {
        service.canRegister(courierPojo);
        Courier savedCourier = service.save(courierPojo);
        return new ResponseEntity<>(savedCourier, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Courier> login(@RequestBody LoginCourierPojo loginCourierPojo) {
            Courier verificationCourier = service.verifyLogin(loginCourierPojo);
            return new ResponseEntity<>(verificationCourier, HttpStatus.OK);

    }
}

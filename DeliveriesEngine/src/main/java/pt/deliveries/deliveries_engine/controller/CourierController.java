package pt.deliveries.deliveries_engine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Courier;

@RestController
@RequestMapping("/deliveries/api/")
public class CourierController {

    @RequestMapping("/register")
    public ResponseEntity<Courier> home(@RequestBody Courier courier) {

        return new ResponseEntity<>(courier, HttpStatus.OK);
    }
}

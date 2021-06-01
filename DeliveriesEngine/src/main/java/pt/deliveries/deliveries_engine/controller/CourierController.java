package pt.deliveries.deliveries_engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourierController {

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }
}

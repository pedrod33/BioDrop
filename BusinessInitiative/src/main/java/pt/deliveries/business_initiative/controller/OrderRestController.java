package pt.deliveries.business_initiative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.deliveries.business_initiative.model.Order;
import pt.deliveries.business_initiative.service.OrderServiceImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/businesses-api/orders")
public class OrderRestController {

    @Autowired
    private OrderServiceImpl service;


    @GetMapping("/allOrders")
    public List<Order> findAllOrders() {
        return service.findAllOrders();
    }

    @GetMapping("/")
    public ResponseEntity<Order> findOrderById(@RequestParam Long orderId) {
        Order found = service.findOrderById(orderId);

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(found, status);
    }

    @PutMapping(value = "/updateProductsOrder")
    public ResponseEntity<Order> updateProductsOrder(@RequestParam Long clientId, @RequestParam Long productId, @RequestParam Integer amount) {
        Order saved = service.updateProductsOrder(clientId, productId, amount);

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(saved, status);
    }

    @PutMapping(value = "/updateStatus")
    public ResponseEntity<Order> updateStatus(@RequestParam Long clientId, @RequestParam String orderStatus) {
        Order saved = service.updateStatus(clientId, orderStatus);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(saved, status);
    }

    @PutMapping(value = "/updateAddressOrder")
    public ResponseEntity<Order> updateAddressOrder(@RequestParam Long clientId, @RequestParam Long productId, @RequestParam Integer amount) {
        Order saved = service.updateProductsOrder(clientId, productId, amount);

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(saved, status);
    }

}

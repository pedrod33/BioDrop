package pt.deliveries.business_initiative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Order;
import pt.deliveries.business_initiative.pojo.AddressPOJO;
import pt.deliveries.business_initiative.service.OrderServiceImpl;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/businesses-api/orders")
public class OrderRestController {

    @Autowired
    private OrderServiceImpl service;


    @GetMapping("/allOrders")
    public List<Order> findAllOrders() {
        return service.findAllOrders();
    }

    @GetMapping("/findByStatus")
    public List<Order> findOrdersByStatus(@RequestParam String status) {
        return service.findAllOrdersByStatus(status);
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

    @PutMapping(value = "/updateOrderAddress")
    public ResponseEntity<Order> updateOrderAddress(@RequestBody AddressPOJO addressPOJO, @RequestParam Long clientId) {
        Order saved = service.updateOrderAddress(clientId, addressPOJO);

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(saved, status);
    }

}
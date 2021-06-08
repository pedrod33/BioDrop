package pt.deliveries.business_initiative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.service.ProductServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/businesses-api/products")
public class ProductRestController {

    @Autowired
    private ProductServiceImpl service;

    private static final Logger logger
            = Logger.getLogger(
            ProductRestController.class.getName());

    @GetMapping("/allProducts")
    public List<Product> findAllProducts() {
        return service.findAllProducts();
    }


    @PostMapping("/save")
    public ResponseEntity<Store> saveProductInStore(@RequestBody Product product, @RequestParam Long storeId) {
        HttpStatus status;
        Store saved = service.saveProd(product, storeId);

        if (saved != null) {
            logger.log(Level.INFO, "Store with id {0} was saved", storeId);
            status = HttpStatus.CREATED;
            return new ResponseEntity<>(saved, status);
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(saved, status);
        }
    }
}

package pt.deliveries.business_initiative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.service.StoreServiceImpl;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/businesses-api")
public class StoreRestController {

    @Autowired
    private StoreServiceImpl service;


    @GetMapping("/stores")
    public List<Store> findAllStores() {
        return service.findAllStores();
    }

    @GetMapping("/productsIn")
    public Set<Product> findAllProductsInStore(@RequestParam Long storeId) {
        return service.findAllProductsInStore(storeId);
    }

    @GetMapping("/store-name")
    public List<Store> findStoreByName(@RequestParam String name) {
         return service.findByName(name);
    }

    @GetMapping("/store-city")
    public List<Store> findStoreByCity(@RequestParam String city) {
        return service.findByCity(city);
    }

    @GetMapping("/store-address")
    public List<Store> findStoreByAddress(@RequestParam String address) {
        return service.findByAddress(address);
    }

    @GetMapping("/store-coords")
    public Store findStoreByCoordinates(@RequestParam double lat, @RequestParam double lng) {
        return service.findByLatAndLng(lat, lng);
    }


    @PostMapping("/saveStore")
    public ResponseEntity<Store> saveStore(@RequestBody Store store) {
        HttpStatus status = HttpStatus.CREATED;
        Store saved = service.saveStore(store);
        return new ResponseEntity<>(saved, status);
    }

}

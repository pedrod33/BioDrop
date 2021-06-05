package pt.deliveries.business_iniciative.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.service.StoreServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/businesses-api")
public class StoreRestController {

    @Autowired
    private StoreServiceImpl service;

    private static final Logger logger
            = Logger.getLogger(
            StoreRestController.class.getName());

    @GetMapping("/stores")
    public List<Store> findAllStores() {
        return service.findAllStores();
    }

    @GetMapping("/store")
    public Store findStore(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "address", required = false) String address) {

        if (name != null)
            return service.findByName(name);
        else if (address != null)
            return service.findByAddress(address);
        else
            return null;
    }

    @GetMapping("/store-coords")
    public Store findStoreByCoordinates(@RequestParam double lat, @RequestParam double lng) {
        return service.findByLatAndLng(lat, lng);
    }


    @PostMapping(value = "/saveStore")
    public ResponseEntity<Store> saveStore(@RequestBody Store store) {
            HttpStatus status = HttpStatus.CREATED;
            Store saved = service.save(store);
            return new ResponseEntity<>(saved, status);
    }

}

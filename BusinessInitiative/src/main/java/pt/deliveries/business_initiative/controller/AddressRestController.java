package pt.deliveries.business_initiative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.AddressSaveForClientPOJO;
import pt.deliveries.business_initiative.service.AddressServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/businesses-api/addresses")
public class AddressRestController {

    @Autowired
    private AddressServiceImpl service;

    private static final Logger logger
            = Logger.getLogger(
            AddressRestController.class.getName());


    @GetMapping("/allAddresses")
    public List<Address> findAllAddresses() {
        return service.findAllAddresses();
    }


    @PutMapping("/update-client-address")
    public ResponseEntity<Client> updateClientAddress(@RequestBody AddressSaveForClientPOJO address, @RequestParam Long clientId) {
        HttpStatus status;
        Client saved = service.updateClientAddress(address, clientId);

        logger.log(Level.INFO, "Client with id {0} was updated", clientId);
        status = HttpStatus.OK;
        return new ResponseEntity<>(saved, status);
    }

}

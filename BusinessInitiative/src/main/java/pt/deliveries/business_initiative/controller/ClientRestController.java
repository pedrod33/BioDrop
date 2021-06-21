package pt.deliveries.business_initiative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
import pt.deliveries.business_initiative.service.ClientServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/businesses-api/clients")
public class ClientRestController {

    @Autowired
    private ClientServiceImpl service;

    private static final Logger logger
            = Logger.getLogger(
            ClientRestController.class.getName());

    @GetMapping("/allClients")
    public List<Client> findAllClients() {
        return service.findAll();
    }

    @GetMapping("/")
    public Client findClientById(@RequestParam Long clientId) {
        return service.findById(clientId);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Client> register(@RequestBody ClientRegistrationPOJO client) {
        service.verifyRegister(client);

        var saved = service.registerClient(client);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(saved, status);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Client> login(@RequestBody ClientLoginPOJO client) {
        var loggedClient = service.verifyLogin(client);

        logger.log(Level.INFO, "Client {0} logged in", client.getEmail());
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(loggedClient, status);
    }
}

package pt.deliveries.business_iniciative.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.service.ClientServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/businesses-api")
public class ClientRestController {

    @Autowired
    private ClientServiceImpl service;

    private static final Logger logger
            = Logger.getLogger(
            ClientRestController.class.getName());

    @GetMapping("/")
    public String home() {
        return "Hello Docker World !";
    }


    @GetMapping("/clients")
    public List<Client> findAllClients() {
        return service.findAll();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Client> register(@RequestBody Client client) {

        if (service.exists(client)) {
            logger.log(Level.INFO, "Client already exists");
            HttpStatus status = HttpStatus.IM_USED;
            return new ResponseEntity<>(client, status);
        }

        logger.log(Level.INFO, client.toString());
        HttpStatus status = HttpStatus.CREATED;
        Client saved = service.save(client);
        return new ResponseEntity<>(saved, status);
    }

    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody Client client) {
        logger.log(Level.INFO, client.toString());
        Client loggedClient = service.verifyLogin(client);

        if (loggedClient != null) {
            logger.log(Level.INFO, "Client {0} logged in", client.getEmail());
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(loggedClient, status);
        } else {
            logger.log(Level.INFO, "Client email or password is incorrect");
            HttpStatus status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(client, status);
        }
    }
}
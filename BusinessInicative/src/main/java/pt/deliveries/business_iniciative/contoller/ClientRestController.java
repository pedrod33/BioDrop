package pt.deliveries.business_iniciative.contoller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.service.ClientServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
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
    public ResponseEntity register(@RequestBody Client client) {
        Boolean b = service.verifyRegister(client);

        if( Boolean.FALSE.equals(b) ) {
            logger.log(Level.INFO, "Client already exists");
            HttpStatus status = HttpStatus.IM_USED;
            return new ResponseEntity<>("Email ou numero de telefone ja se encontram em uso.", status);
        } else {
            HttpStatus status = HttpStatus.CREATED;
            Client saved = service.save(client);
            return new ResponseEntity<>(saved, status);
        }
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity login(@RequestBody Client client) {
        Client loggedClient = service.verifyLogin(client);

        if (loggedClient != null) {
            logger.log(Level.INFO, "Client {0} logged in", client.getEmail());
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(loggedClient, status);
        } else {
            logger.log(Level.INFO, "Client email or password is incorrect");
            HttpStatus status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>("Email ou password incorretos", status);
        }
    }
}

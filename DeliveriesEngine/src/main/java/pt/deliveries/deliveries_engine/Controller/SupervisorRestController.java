package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;
import pt.deliveries.deliveries_engine.Service.SupervisorServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/deliveries-api/supervisor")
public class SupervisorRestController {

    Logger logger = Logger.getLogger(SupervisorRestController.class.getName());

    @Autowired
    private SupervisorServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<Supervisor> createSupervisor(@RequestBody RegisterSupervisorPojo supervisorPojo){
        service.existsRegister(supervisorPojo);
        Supervisor created = service.create(supervisorPojo);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

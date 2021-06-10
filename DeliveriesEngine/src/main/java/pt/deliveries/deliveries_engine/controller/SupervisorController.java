package pt.deliveries.deliveries_engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Service.SupervisorServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/deliveries-api/supervisor")
public class SupervisorController {

    Logger logger = Logger.getLogger(SupervisorController.class.getName());

    @Autowired
    private SupervisorServiceImpl service;

    @RequestMapping("/register")
    public ResponseEntity<Supervisor> createSupervisor(@RequestBody Supervisor supervisor){
        if(service.exists(supervisor)){
            logger.log(Level.INFO,"true");
            return new ResponseEntity<>(supervisor, HttpStatus.IM_USED);
        }
        Supervisor created = service.create(supervisor);
        if(created==null){
            logger.log(Level.INFO,"false but is null");
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        logger.log(Level.INFO,"false");
        logger.log(Level.INFO,created.toString());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

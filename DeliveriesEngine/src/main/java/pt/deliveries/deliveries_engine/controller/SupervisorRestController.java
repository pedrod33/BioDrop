package pt.deliveries.deliveries_engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;
import pt.deliveries.deliveries_engine.Service.SupervisorServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/deliveries-api/supervisor")
public class SupervisorRestController {

    Logger logger = Logger.getLogger(SupervisorRestController.class.getName());

    @Autowired
    private SupervisorServiceImpl service;

    @RequestMapping("/register")
    public ResponseEntity<Supervisor> createSupervisor(@RequestBody RegisterSupervisorPojo supervisorPojo){
        if(service.existsRegister(supervisorPojo)){
            logger.log(Level.INFO,"true");
            return new ResponseEntity<>(null, HttpStatus.IM_USED);
        }
        Supervisor created = service.create(supervisorPojo);
        if(created==null){
            logger.log(Level.INFO,"false but is null");
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        logger.log(Level.INFO,"false");
        logger.log(Level.INFO,created.toString());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

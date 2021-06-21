package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.SupervisorEmailIsUsedException;
import pt.deliveries.deliveries_engine.Exception.SupervisorMailAndPasswordDoesNotMatchException;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;
import pt.deliveries.deliveries_engine.Repository.SupervisorRepository;

@Service
@Transactional
public class SupervisorServiceImpl{

    @Autowired
    private SupervisorRepository repository;

    public Supervisor create(RegisterSupervisorPojo supervisorPojo) {
        Supervisor supervisor = new Supervisor();
        supervisor.setEmail(supervisorPojo.getEmail());
        supervisor.setName(supervisorPojo.getName());
        supervisor.setPassword(supervisorPojo.getPassword());
        return repository.save(supervisor);
    }

    public boolean existsRegister(RegisterSupervisorPojo supervisorPojo) {
        if (repository.findByEmail(supervisorPojo.getEmail()) == null) {
            return false;
        }
        throw new SupervisorEmailIsUsedException("This email is already being used!");
    }

    public boolean credentialValidity(RegisterSupervisorPojo supervisorPojo) {
        if (repository.findByEmailAndPassword(supervisorPojo.getEmail(), supervisorPojo.getPassword()) == null) {
            throw new SupervisorMailAndPasswordDoesNotMatchException("This password does not match the email!");
        }
        return true;
    }
}

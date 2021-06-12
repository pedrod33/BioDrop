package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;
import pt.deliveries.deliveries_engine.Repository.SupervisorRepository;

@Service
@Transactional
public class SupervisorServiceImpl implements SupervisorService{

    @Autowired
    private SupervisorRepository repository;

    @Override
    public Supervisor create(RegisterSupervisorPojo supervisorPojo) {
        Supervisor supervisor = new Supervisor();
        supervisor.setEmail(supervisorPojo.getEmail());
        supervisor.setName(supervisorPojo.getName());
        supervisor.setPassword(supervisorPojo.getPassword());
        return repository.save(supervisor);
    }

    @Override
    public boolean existsRegister(RegisterSupervisorPojo supervisorPojo) {
        if (repository.findByEmail(supervisorPojo.getEmail()) == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean credentialValidity(RegisterSupervisorPojo supervisorPojo) {
        if (repository.findByEmailAndPassword(supervisorPojo.getEmail(), supervisorPojo.getPassword()) == null) {
            return false;
        }
        return true;
    }
}

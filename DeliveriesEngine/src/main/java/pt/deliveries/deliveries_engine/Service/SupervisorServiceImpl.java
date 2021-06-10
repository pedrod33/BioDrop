package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Repository.SupervisorRepository;

@Service
@Transactional
public class SupervisorServiceImpl implements SupervisorService{

    @Autowired
    private SupervisorRepository repository;

    @Override
    public Supervisor create(Supervisor supervisor) {
        return repository.save(supervisor);
    }

    @Override
    public boolean exists(Supervisor supervisor) {
        if (repository.findByEmail(supervisor.getEmail()) == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean credentialValidity(Supervisor supervisor) {
        if (repository.findByEmailAndPassword(supervisor.getEmail(), supervisor.getPassword()) == null) {
            return false;
        }
        return true;
    }
}

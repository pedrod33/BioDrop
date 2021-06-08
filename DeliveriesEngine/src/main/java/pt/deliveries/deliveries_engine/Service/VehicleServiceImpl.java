package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository repository;

    Logger logger = Logger.getLogger(VehicleServiceImpl.class.getName());
    @Override
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Override
    public boolean exists(Vehicle vehicle) {
        if(repository.findByType(vehicle.getType())!=null){
            logger.log(Level.INFO,"true");
            return true;
        }
        logger.log(Level.INFO,"false");
        return false;
    }

    @Override
    public Vehicle findById(Vehicle vehicle){
        return null;
    }
}

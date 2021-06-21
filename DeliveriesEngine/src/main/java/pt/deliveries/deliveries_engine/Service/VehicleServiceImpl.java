package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeIsUsedException;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class VehicleServiceImpl{

    Logger logger = Logger.getLogger(VehicleServiceImpl.class.getName());

    @Autowired
    VehicleRepository repository;

    public Vehicle create(String vehicleType) {
        Vehicle vehicle = new Vehicle(vehicleType);
        return repository.save(vehicle);
    }

    public boolean exists(String vehicle) {
        logger.log(Level.INFO, vehicle);
        if(repository.findByType(vehicle)!=null){
            throw new VehicleTypeIsUsedException("This type of vehicle already exists");
        }
        return false;
    }

    public Vehicle findById(Vehicle vehicle){
        return null;
    }

    public List<Vehicle> findAllVehicles() {
        return repository.findAll();
    }
}

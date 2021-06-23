package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeIsUsedException;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository repository;

    @Override
    public Vehicle create(String vehicleType) {
        Vehicle vehicle = new Vehicle(vehicleType);
        return repository.save(vehicle);
    }

    @Override
    public boolean exists(String vehicle) {
        if(repository.findByType(vehicle)!=null){
            throw new VehicleTypeIsUsedException("This type of vehicle already exists");
        }
        return false;
    }

    @Override
    public Vehicle findById(Vehicle vehicle){
        return null;
    }
}

package pt.deliveries.deliveries_engine.Service;

import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Vehicle;

public interface VehicleService {
    Vehicle create(Vehicle vehicle);

    boolean exists(Vehicle vehicle);

    Vehicle findById(Vehicle vehicle);
}

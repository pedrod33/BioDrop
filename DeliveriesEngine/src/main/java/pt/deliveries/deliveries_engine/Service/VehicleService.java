package pt.deliveries.deliveries_engine.Service;

import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Vehicle;

public interface VehicleService {
    Vehicle create(String vehicle);

    boolean exists(String vehicle);

    Vehicle findById(Vehicle vehicle);
}

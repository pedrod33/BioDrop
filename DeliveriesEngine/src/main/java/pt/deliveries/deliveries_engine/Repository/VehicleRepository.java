package pt.deliveries.deliveries_engine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.deliveries_engine.Model.Vehicle;

@Repository
public interface VehicleRepository  extends JpaRepository<Vehicle, Long> {

    Vehicle save(Vehicle vehicle);

    Vehicle findById(long id);

    Vehicle findByType(String type);
}

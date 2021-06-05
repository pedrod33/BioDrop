package pt.deliveries.deliveries_engine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.deliveries_engine.Model.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

}
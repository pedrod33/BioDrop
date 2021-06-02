package pt.deliveries.deliveries_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.deliveries_engine.model.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

}
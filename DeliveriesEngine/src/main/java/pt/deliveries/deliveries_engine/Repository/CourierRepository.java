package pt.deliveries.deliveries_engine.Repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.deliveries_engine.Model.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    Courier findById(long id);

    Courier findByEmail(String email);

    Courier findByPhoneNumber(long phoneNumber);
}
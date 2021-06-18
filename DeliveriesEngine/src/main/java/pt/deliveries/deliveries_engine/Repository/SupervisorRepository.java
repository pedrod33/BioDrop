package pt.deliveries.deliveries_engine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;

@Repository
public interface SupervisorRepository  extends JpaRepository<Supervisor, Long> {

    Supervisor save(Supervisor supervisor);

    Supervisor findById(long id);

    Supervisor findByEmail(String email);

    Supervisor findByEmailAndPassword(String email, String password);
}

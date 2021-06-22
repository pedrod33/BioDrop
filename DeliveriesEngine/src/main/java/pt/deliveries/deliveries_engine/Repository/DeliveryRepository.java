package pt.deliveries.deliveries_engine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.deliveries_engine.Model.Delivery;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findById(long id);
    List<Delivery> findAll();
    Delivery findDeliveryByOrderId(long id);
    boolean existsOrderFromDeliveryById(Long id);
}

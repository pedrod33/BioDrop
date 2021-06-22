package pt.deliveries.business_initiative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_initiative.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findById(long orderId);

}

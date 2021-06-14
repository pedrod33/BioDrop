package pt.deliveries.business_initiative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_initiative.model.Order_Product;

@Repository
public interface Order_ProductRepository extends JpaRepository<Order_Product, Long> {

}

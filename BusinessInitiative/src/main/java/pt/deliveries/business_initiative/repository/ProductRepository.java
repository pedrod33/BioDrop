package pt.deliveries.business_initiative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_initiative.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(long id);

}
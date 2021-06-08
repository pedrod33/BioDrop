package pt.deliveries.business_iniciative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(long id);

}
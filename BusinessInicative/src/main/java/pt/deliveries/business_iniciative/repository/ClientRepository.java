package pt.deliveries.business_iniciative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_iniciative.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
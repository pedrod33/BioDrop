package pt.deliveries.business_initiative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_initiative.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
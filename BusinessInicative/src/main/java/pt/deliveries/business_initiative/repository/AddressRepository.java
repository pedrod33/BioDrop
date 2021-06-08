package pt.deliveries.business_iniciative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_iniciative.model.Address;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.model.Store;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
package pt.deliveries.business_initiative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_initiative.model.Store;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByName(String name);

    Store findByAddress_LatitudeAndAddress_Longitude(double latitude, double longitude);

    List<Store> findByAddress_CompleteAddress(String address);

    List<Store> findByAddress_City(String city);

}
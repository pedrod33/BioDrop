package pt.deliveries.business_iniciative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_iniciative.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findById(long id);

    Store findByName(String name);

    Store findByAddress(String address);

    Store findByLatitudeAndLongitude(double lat, double lng);
}
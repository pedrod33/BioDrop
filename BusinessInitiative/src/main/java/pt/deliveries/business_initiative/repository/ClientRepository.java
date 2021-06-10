package pt.deliveries.business_initiative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.deliveries.business_initiative.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findById(long id);

    Client findByEmail(String email);

    Client findByPhoneNumber(String phoneNumber);

}
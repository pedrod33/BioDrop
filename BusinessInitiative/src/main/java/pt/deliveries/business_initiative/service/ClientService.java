package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    void verifyRegister(ClientRegistrationPOJO clientPOJO);

    Client registerClient(ClientRegistrationPOJO clientPOJO);

    Client save(Client client);

    Client verifyLogin(ClientLoginPOJO clientPOJO);

    Client findById(Long clientId);
}

package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    Boolean verifyRegister(Client client);

    Client save(Client client);

    Client verifyLogin(Client client);
}

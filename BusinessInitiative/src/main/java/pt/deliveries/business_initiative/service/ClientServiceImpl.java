package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.repository.ClientRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private static final Logger logger
            = Logger.getLogger(
            ClientServiceImpl.class.getName());


    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Boolean verifyRegister(Client client) {
        logger.log(Level.INFO, "Checking if clients email and phone number exists ...");
        return clientRepository.findByEmail(client.getEmail()) == null && clientRepository.findByPhoneNumber(client.getPhoneNumber()) == null;
    }

    @Override
    public Client save(Client client) {
        logger.log(Level.INFO, "Saving new client ...");
        return clientRepository.save(client);
    }

    @Override
    public Client verifyLogin(Client client) {
        logger.log(Level.INFO, "Verifying client credentials ...");

        Client loggedClient = clientRepository.findByEmail(client.getEmail());

        if (loggedClient != null) {
            if ( loggedClient.getPassword().equals(client.getPassword())) {
                return loggedClient;
            } else {
                return null;
            }
        } else
            return null;
    }

    @Override
    public Client findById(Long clientId) {
        logger.log(Level.INFO, "Finding client by id ...");

        return clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client id not found"));
    }
}




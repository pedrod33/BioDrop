package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.business_initiative.exception.ClientEmailOrPasswordIncorrectException;
import pt.deliveries.business_initiative.exception.ClientEmailOrPhoneNumberInUseException;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
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
    public void verifyRegister(ClientRegistrationPOJO clientPOJO) {
        logger.log(Level.INFO, "Checking if clients email and phone number exists ...");
        if( clientRepository.findByEmail(clientPOJO.getEmail()) != null || clientRepository.findByPhoneNumber(clientPOJO.getPhoneNumber()) != null )
            throw new ClientEmailOrPhoneNumberInUseException("Email ou phone number already in use.");
    }

    @Override
    public Client registerClient(ClientRegistrationPOJO clientPOJO) {
        var client = new Client();
        client.setName(clientPOJO.getName());
        client.setEmail(clientPOJO.getEmail());
        client.setPassword(clientPOJO.getPassword());
        client.setGender(clientPOJO.getGender());
        client.setPhoneNumber(clientPOJO.getPhoneNumber());

        System.out.println(clientPOJO.toString());
        System.out.println(client.toString());

        logger.log(Level.INFO, "Saving new client ...");
        return clientRepository.save(client);
    }

    @Override
    public Client save(Client client) {
        logger.log(Level.INFO, "Saving new client ...");
        return clientRepository.save(client);
    }

    @Override
    public Client verifyLogin(ClientLoginPOJO clientPOJO) {
        var client = new Client();
        client.setEmail(clientPOJO.getEmail());
        client.setPassword(clientPOJO.getPassword());

        logger.log(Level.INFO, "Verifying client credentials ...");
        var loggedClient = clientRepository.findByEmail(client.getEmail());
        if (loggedClient != null && loggedClient.getPassword().equals(client.getPassword()))
            return loggedClient;

        throw new ClientEmailOrPasswordIncorrectException("Email or Password Incorrect");
    }

    @Override
    public Client findById(Long clientId) {
        logger.log(Level.INFO, "Finding client by id ...");

        return clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client id not found"));
    }
}




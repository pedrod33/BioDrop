package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_initiative.exception.AddressNotFoundException;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.AddressSaveForClientPOJO;
import pt.deliveries.business_initiative.repository.AddressRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository repository;

    @Autowired
    ClientService clientService;

    @Autowired
    StoreService storeService;

    private static final Logger logger
            = Logger.getLogger(
            AddressServiceImpl.class.getName());

    @Override
    public List<Address> findAllAddresses() {
        return repository.findAll();
    }

    @Override
    public Client updateClientAddress(AddressSaveForClientPOJO addressPOJO, Long clientId) {
        Address address = new Address();
        address.setCity(addressPOJO.getCity());
        address.setCompleteAddress(addressPOJO.getCompleteAddress());
        address.setLatitude(addressPOJO.getLatitude());
        address.setLongitude(addressPOJO.getLongitude());

        Client clientFound = clientService.findById(clientId);

        logger.log(Level.INFO, "Associating Address to client {0} and saving ...", clientId);
        clientFound.getAddresses().add(address);
        return clientService.save(clientFound);
    }

}

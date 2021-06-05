package pt.deliveries.business_iniciative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.repository.ClientRepository;
import pt.deliveries.business_iniciative.repository.StoreRepository;
import pt.deliveries.business_iniciative.service.StoreService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository repository;

    private static final Logger logger
            = Logger.getLogger(
            ClientServiceImpl.class.getName());


    @Override
    public List<Store> findAllStores() {
        logger.log(Level.INFO, "Finding all stores ...");
        return repository.findAll();
    }

    @Override
    public Store findByName(String name) {
        logger.log(Level.INFO, "Finding store by name ...");
        return repository.findByName(name);
    }

    @Override
    public Store findByAddress(String address) {
        logger.log(Level.INFO, "Finding store by address ...");
        return repository.findByAddress(address);
    }

    @Override
    public Store findByLatAndLng(double lat, double lng) {
        logger.log(Level.INFO, "Finding store by coordinates ...");
        return repository.findByLatitudeAndLongitude(lat, lng);
    }

    @Override
    public Store save(Store store) {
        logger.log(Level.INFO, "Saving new store ...");
        return repository.save(store);
    }

}

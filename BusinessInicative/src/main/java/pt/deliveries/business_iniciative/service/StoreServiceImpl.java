package pt.deliveries.business_iniciative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.repository.ProductRepository;
import pt.deliveries.business_iniciative.repository.StoreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository repository;

    @Autowired
    private ProductRepository prodRepository;

    private static final Logger logger
            = Logger.getLogger(
            StoreServiceImpl.class.getName());


    @Override
    public List<Store> findAllStores() {
        logger.log(Level.INFO, "Finding all stores ...");
        return repository.findAll();
    }

    @Override
    public Set<Product> findAllProductsInStore(Long storeId) {
        logger.log(Level.INFO, "Finding products for store with id: {0} ...", storeId);

        Store found = repository.findById(storeId).orElse(null);

        if( found != null ) {
            logger.log(Level.INFO, "Store found, returning products ...");
            return found.getProducts();
        } else {
            logger.log(Level.INFO, "Store with id {0} wasnt found", storeId);
            return new HashSet<>();
        }

    }

    @Override
    public Store findById(Long id) {
        logger.log(Level.INFO, "Finding store by id ...");
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Store> findByName(String name) {
        logger.log(Level.INFO, "Finding store by name ...");
        return repository.findByName(name);
    }

    @Override
    public List<Store> findByAddress(String address) {
        logger.log(Level.INFO, "Finding store by address ...");
        return repository.findByAddress(address);
    }

    @Override
    public Store findByLatAndLng(double lat, double lng) {
        logger.log(Level.INFO, "Finding store by coordinates ...");
        return repository.findByLatitudeAndLongitude(lat, lng);
    }

    @Override
    public Store saveStore(Store store) {
        logger.log(Level.INFO, "Saving new store ...");
        return repository.save(store);
    }

}

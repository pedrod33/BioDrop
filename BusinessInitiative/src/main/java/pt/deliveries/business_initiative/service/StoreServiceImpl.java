package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_initiative.exception.StoreNotFoundException;
import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.pojo.StoreSavePOJO;
import pt.deliveries.business_initiative.repository.StoreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository repository;

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

        Store found = repository.findById(storeId).orElseThrow(() -> new StoreNotFoundException("Store not found"));

        logger.log(Level.INFO, "Store found, returning products ...");
        return found.getProducts();
    }

    @Override
    public Store findById(Long id) {
        logger.log(Level.INFO, "Finding store by id ...");
        return repository.findById(id).orElseThrow(() -> new StoreNotFoundException("Store not found"));
    }

    @Override
    public List<Store> findByName(String name) {
        logger.log(Level.INFO, "Finding store by name ...");
        return repository.findByName(name);
    }

    @Override
    public List<Store> findByCity(String city) {
        logger.log(Level.INFO, "Finding store by city ...");
        return repository.findByAddress_City(city);
    }

    @Override
    public List<Store> findByAddress(String address) {
        logger.log(Level.INFO, "Finding store by address ...");
        return repository.findByAddress_CompleteAddress(address);
    }

    @Override
    public Store findByLatAndLng(double lat, double lng) {
        logger.log(Level.INFO, "Finding store by coordinates ...");
        return repository.findByAddress_LatitudeAndAddress_Longitude(lat, lng);
    }

    @Override
    public Store save(Store store) {
        logger.log(Level.INFO, "Saving new store ...");
        return repository.save(store);
    }

    @Override
    public Store createStore(StoreSavePOJO storePOJO) {
        Store store = new Store();
        store.setName(storePOJO.getName());
        store.setAddress(storePOJO.getAddress());
        store.setProducts(storePOJO.getProducts());

        logger.log(Level.INFO, "Saving new store ...");
        return repository.save(store);
    }

}

package pt.deliveries.business_iniciative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.repository.ProductRepository;
import pt.deliveries.business_iniciative.repository.StoreRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    StoreRepository storeRepository;

    private static final Logger logger
            = Logger.getLogger(
            ProductServiceImpl.class.getName());

    @Override
    public List<Product> findAllProducts() {
        return repository.findAll();
    }


    @Override
    public Store saveProd(Product product, Long store_id) {
        Store store_found = findStoreById(store_id);

        if( store_found != null ) {
            logger.log(Level.INFO, "Associating store {0} to the product and saving ...", store_id);
            //product.setStore(store_found);
            store_found.getProducts().add(product);
            //return repository.save(product);
            return storeRepository.save(store_found);
        }

        logger.log(Level.WARNING, "Store not found for id {0} ...", store_id);
        return null;
    }


    @Override
    public Product findById(Long id) {
        logger.log(Level.INFO, "Finding product by id ...");
        return repository.findById(id).orElse(null);
    }

    @Override
    public Store findStoreById(Long id) {
        logger.log(Level.INFO, "Finding store by id ...");
        return storeRepository.findById(id).orElse(null);
    }

}

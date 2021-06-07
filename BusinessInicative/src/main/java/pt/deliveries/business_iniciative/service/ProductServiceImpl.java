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
    StoreServiceImpl storeService;


    private static final Logger logger
            = Logger.getLogger(
            ProductServiceImpl.class.getName());


    @Override
    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    @Override
    public Store saveProd(Product product, Long store_id) {
        Store store_found = storeService.findById(store_id);

        if( store_found != null ) {
            logger.log(Level.INFO, "Associating store {0} to the product and saving ...", store_id);
            store_found.getProducts().add(product);
            return storeService.saveStore(store_found);
        }

        logger.log(Level.WARNING, "Store not found for id {0} ...", store_id);
        return null;
    }

    @Override
    public Product findById(Long id) {
        logger.log(Level.INFO, "Finding product by id ...");
        return repository.findById(id).orElse(null);
    }


}

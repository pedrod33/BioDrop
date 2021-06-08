package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.repository.ProductRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public Store saveProd(Product product, Long storeId) {
        Store storeFound = storeService.findById(storeId);

        if( storeFound != null ) {
            logger.log(Level.INFO, "Associating store {0} to the product and saving ...", storeId);
            if ( storeFound.getProducts() != null)
                storeFound.getProducts().add(product);
            else {
                Set<Product> productsInStore = new HashSet<>();
                productsInStore.add(product);

                storeFound.setProducts(productsInStore);
            }

            return storeService.saveStore(storeFound);
        }

        logger.log(Level.WARNING, "Store not found for id {0} ...", storeId);
        return null;
    }

    @Override
    public Product findById(Long id) {
        logger.log(Level.INFO, "Finding product by id ...");
        return repository.findById(id).orElse(null);
    }


}

package pt.deliveries.business_iniciative.service;

import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product findById(Long id);

    Store findStoreById(Long id);

    Store save(Product product, Long store_id);

}
